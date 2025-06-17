package panek.szymon.fishcards.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import panek.szymon.fishcards.dto.DeckUpdateRequest;
import panek.szymon.fishcards.dto.mapper.DeckMapper;
import panek.szymon.fishcards.entity.Deck;
import panek.szymon.fishcards.exceptions.DeckNotFoundException;
import panek.szymon.fishcards.repository.DeckRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DeskServiceImplTest {
    @Mock
    private DeckRepository deckRepository;

    @Mock
    private DeckMapper deckMapper;

    @InjectMocks
    private DeckServiceImpl deckService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void getDeckBy_Id_shouldReturnDeck() {
        //given
        String id = "123";
        Deck deck = new Deck();
        deck.setId(id);
        when(deckRepository.findById(id)).thenReturn(Optional.of(deck));

        //when
        Optional<Deck> result = deckService.getDeckById(id);

        //then
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void getAllDecksByUserId_shouldReturnList() {
        //given
        String userId = "123";
        List<Deck> decks = Arrays.asList(new Deck(), new Deck());
        when(deckRepository.findAllByUserId(userId)).thenReturn(decks);

        //when
        List<Deck> result = deckService.getAllDecksByUserId(userId);

        //then
        assertEquals(2, result.size());
        verify(deckRepository, times(1)).findAllByUserId(userId);
    }

    @Test
    void createDeck_shouldSaveAndReturnDeck() {
        //given
        Deck deck = new Deck();
        deck.setId("123");
        when(deckRepository.save(deck)).thenReturn(deck);

        //when
        Deck result = deckService.createDeck(deck);

        //then
        assertEquals("123", result.getId());
        verify(deckRepository, times(1)).save(deck);
    }

    @Test
    void updateDeck_shouldUpdateAndReturnDeck() {
        //given
        String id = "123";
        Deck existingDeck = new Deck();
        existingDeck.setId(id);
        DeckUpdateRequest request = new DeckUpdateRequest();
        when(deckRepository.findById(id)).thenReturn(Optional.of(existingDeck));
        when(deckRepository.save(existingDeck)).thenReturn(existingDeck);

        //when
        Deck result = deckService.updateDeck(id, request);

        //then
        assertEquals(id, result.getId());
        verify(deckMapper, times(1)).updateDeckFromDto(request, existingDeck);
        verify(deckRepository, times(1)).save(existingDeck);
    }

    @Test
    void deleteDeck_shouldDeleteExistingDeck() {
        // given
        String id = "123";
        Deck existingDeck = new Deck();
        existingDeck.setId(id);
        when(deckRepository.findById(id)).thenReturn(Optional.of(existingDeck));

        // when
        deckService.deleteDeck(id);

        // then
        verify(deckRepository, times(1)).findById(id);
        verify(deckRepository, times(1)).delete(existingDeck);
    }


    @Test
    void deleteDeck_shouldThrowExceptionWhenDeckNotFound() {
        // given
        String id = "not_found_id";
        when(deckRepository.findById(id)).thenReturn(Optional.empty());

        // when + then
        assertThrows(DeckNotFoundException.class, () -> deckService.deleteDeck(id));
        verify(deckRepository, times(1)).findById(id);
        verify(deckRepository, never()).delete(any());
    }
}
