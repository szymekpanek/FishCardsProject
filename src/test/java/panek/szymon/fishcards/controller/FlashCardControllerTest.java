package panek.szymon.fishcards.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import panek.szymon.fishcards.dto.FlashCardCreateRequest;
import panek.szymon.fishcards.dto.FlashCardUpdateRequest;
import panek.szymon.fishcards.entity.FlashCard;
import panek.szymon.fishcards.service.interfaces.FlashCardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlashCardControllerTest {

    @Mock
    private FlashCardService flashCardService;

    @InjectMocks
    private FlashCardController flashCardController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getFlashCardById_shouldReturnFlashCard() {
        String id = "123";
        FlashCard flashCard = new FlashCard();
        flashCard.setId(id);

        when(flashCardService.getFlashCardById(id)).thenReturn(Optional.of(flashCard));

        ResponseEntity<Optional<FlashCard>> response = flashCardController.getFlashCardById(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(id, response.getBody().get().getId());
    }

    @Test
    void getFlashCardsByUserId_shouldReturnList() {
        String userId = "user123";
        List<FlashCard> flashCards = Arrays.asList(new FlashCard(), new FlashCard());

        when(flashCardService.getAllFlashCardsByUserId(userId)).thenReturn(flashCards);

        ResponseEntity<List<FlashCard>> response = flashCardController.getFlashCardsByUserId(userId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getFlashCardsByUserId_shouldReturnNoContentWhenEmpty() {
        String userId = "user123";

        when(flashCardService.getAllFlashCardsByUserId(userId)).thenReturn(Collections.emptyList());

        ResponseEntity<List<FlashCard>> response = flashCardController.getFlashCardsByUserId(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateFlashCard_shouldReturnUpdatedFlashCard() {
        String id = "123";
        FlashCardUpdateRequest request = new FlashCardUpdateRequest();
        FlashCard updatedCard = new FlashCard();
        updatedCard.setId(id);

        when(flashCardService.updateFlashCard(id, request)).thenReturn(updatedCard);

        ResponseEntity<FlashCard> response = flashCardController.updateFlashCard(id, request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void deleteFlashCard_shouldReturnNoContent() {
        String id = "123";

        doNothing().when(flashCardService).deleteFlashCard(id);

        ResponseEntity<Void> response = flashCardController.deleteFlashCard(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(flashCardService, times(1)).deleteFlashCard(id);
    }

    @Test
    void shouldCreateMultipleFlashCards() {
        FlashCardCreateRequest request1 = new FlashCardCreateRequest("Question 1", "Answer 1", "user1");
        FlashCardCreateRequest request2 = new FlashCardCreateRequest("Question 2", "Answer 2", "user1");
        List<FlashCardCreateRequest> requests = List.of(request1, request2);

        FlashCard flashCard1 = new FlashCard();
        flashCard1.setId("1");
        flashCard1.setQuestion("Question 1");
        flashCard1.setAnswer("Answer 1");
        flashCard1.setUserId("user1");
        flashCard1.setTopicId(null);
        flashCard1.setDeckId(null);
        flashCard1.setDifficulty(null);

        FlashCard flashCard2 = new FlashCard();
        flashCard2.setId("2");
        flashCard2.setQuestion("Question 2");
        flashCard2.setAnswer("Answer 2");
        flashCard2.setUserId("user1");
        flashCard2.setTopicId(null);
        flashCard2.setDeckId(null);
        flashCard2.setDifficulty(null);

        List<FlashCard> createdFlashCards = List.of(flashCard1, flashCard2);

        when(flashCardService.createMultipleFlashCardsFromDto(requests)).thenReturn(createdFlashCards);

        ResponseEntity<List<FlashCard>> response = flashCardController.createMultipleFlashCards(requests);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(2, response.getBody().size());
        assertEquals("Question 1", response.getBody().get(0).getQuestion());
        assertEquals("Answer 2", response.getBody().get(1).getAnswer());
    }

}
