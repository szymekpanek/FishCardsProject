package panek.szymon.fishcards.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import panek.szymon.fishcards.dto.FlashCardCreateRequest;
import panek.szymon.fishcards.dto.FlashCardUpdateRequest;
import panek.szymon.fishcards.dto.mapper.FlashCardMapper;
import panek.szymon.fishcards.entity.FlashCard;
import panek.szymon.fishcards.exceptions.FlashCardNotFoundException;
import panek.szymon.fishcards.repository.FlashCardRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlashCardServiceImplTest {

    @Mock
    private FlashCardRepository flashCardRepository;

    @Mock
    private FlashCardMapper flashCardMapper;

    @InjectMocks
    private FlashCardServiceImpl flashCardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFlashCardById_shouldReturnFlashCard() {
        // given
        String id = "123";
        FlashCard flashCard = new FlashCard();
        flashCard.setId(id);
        when(flashCardRepository.findById(id)).thenReturn(Optional.of(flashCard));

        // when
        Optional<FlashCard> result = flashCardService.getFlashCardById(id);

        // then
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void getAllFlashCards_shouldReturnList() {
        // given
        List<FlashCard> flashCards = Arrays.asList(new FlashCard(), new FlashCard());
        when(flashCardRepository.findAll()).thenReturn(flashCards);

        // when
        List<FlashCard> result = flashCardService.getAllFlashCards();

        // then
        assertEquals(2, result.size());
    }

    @Test
    void createFlashCard_shouldSaveAndReturnFlashCard() {
        // given
        FlashCard flashCard = new FlashCard();
        flashCard.setId("123");
        when(flashCardRepository.save(flashCard)).thenReturn(flashCard);

        // when
        FlashCard result = flashCardService.createFlashCard(flashCard);

        // then
        assertEquals("123", result.getId());
        verify(flashCardRepository, times(1)).save(flashCard);
    }

    @Test
    void updateFlashCard_shouldUpdateAndReturnFlashCard() {
        // given
        String id = "123";
        FlashCard existingCard = new FlashCard();
        existingCard.setId(id);
        FlashCardUpdateRequest request = new FlashCardUpdateRequest();

        when(flashCardRepository.findById(id)).thenReturn(Optional.of(existingCard));
        when(flashCardRepository.save(existingCard)).thenReturn(existingCard);

        // when
        FlashCard result = flashCardService.updateFlashCard(id, request);

        // then
        assertEquals(id, result.getId());
        verify(flashCardMapper, times(1)).updateFlashCardFromDto(request, existingCard);
        verify(flashCardRepository, times(1)).save(existingCard);
    }

    @Test
    void updateFlashCard_shouldThrowExceptionWhenNotFound() {
        // given
        String id = "123";
        FlashCardUpdateRequest request = new FlashCardUpdateRequest();

        when(flashCardRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(FlashCardNotFoundException.class, () -> flashCardService.updateFlashCard(id, request));
        verify(flashCardMapper, never()).updateFlashCardFromDto(any(), any());
        verify(flashCardRepository, never()).save(any());
    }

    @Test
    void deleteFlashCard_shouldDeleteFlashCard() {
        // given
        String id = "123";
        FlashCard flashCard = new FlashCard();
        flashCard.setId(id);

        when(flashCardRepository.findById(id)).thenReturn(Optional.of(flashCard));
        doNothing().when(flashCardRepository).delete(flashCard);

        // when
        flashCardService.deleteFlashCard(id);

        // then
        verify(flashCardRepository, times(1)).delete(flashCard);
    }

    @Test
    void deleteFlashCard_shouldThrowExceptionWhenNotFound() {
        // given
        String id = "123";
        when(flashCardRepository.findById(id)).thenReturn(Optional.empty());

        // when & then
        assertThrows(FlashCardNotFoundException.class, () -> flashCardService.deleteFlashCard(id));
        verify(flashCardRepository, never()).delete(any());
    }

    @Test
    void getAllFlashCardsByUserId_shouldReturnList() {
        // given
        String userId = "user123";
        List<FlashCard> flashCards = Arrays.asList(new FlashCard(), new FlashCard());

        when(flashCardRepository.findAllByUserId(userId)).thenReturn(flashCards);

        // when
        List<FlashCard> result = flashCardService.getAllFlashCardsByUserId(userId);

        // then
        assertEquals(2, result.size());
        verify(flashCardRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    void shouldCreateMultipleFlashCardsFromDto() {
        // given
        FlashCardCreateRequest request1 = new FlashCardCreateRequest("Question 1", "Answer 1", "user1");
        FlashCardCreateRequest request2 = new FlashCardCreateRequest("Question 2", "Answer 2", "user1");
        List<FlashCardCreateRequest> requests = List.of(request1, request2);

        FlashCard flashCard1 = new FlashCard();
        flashCard1.setQuestion("Question 1");
        flashCard1.setAnswer("Answer 1");
        flashCard1.setUserId("user1");
        flashCard1.setTopicId(null);
        flashCard1.setDeckId(null);
        flashCard1.setDifficulty(null);

        FlashCard flashCard2 = new FlashCard();
        flashCard2.setQuestion("Question 2");
        flashCard2.setAnswer("Answer 2");
        flashCard2.setUserId("user1");
        flashCard2.setTopicId(null);
        flashCard2.setDeckId(null);
        flashCard2.setDifficulty(null);

        List<FlashCard> mappedFlashCards = List.of(flashCard1, flashCard2);

        when(flashCardMapper.toFlashCardList(requests)).thenReturn(mappedFlashCards);
        when(flashCardRepository.saveAll(mappedFlashCards)).thenReturn(mappedFlashCards);

        // when
        List<FlashCard> result = flashCardService.createMultipleFlashCardsFromDto(requests);

        // then
        assertEquals(2, result.size());
        verify(flashCardMapper).toFlashCardList(requests);
        verify(flashCardRepository).saveAll(mappedFlashCards);
    }

}
