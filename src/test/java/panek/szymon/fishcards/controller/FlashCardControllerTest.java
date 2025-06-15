package panek.szymon.fishcards.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
        // given
        String id = "123";
        FlashCard flashCard = new FlashCard();
        flashCard.setId(id);

        when(flashCardService.getFlashCardById(id)).thenReturn(Optional.of(flashCard));

        // when
        ResponseEntity<Optional<FlashCard>> response = flashCardController.getFlashCardById(id);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isPresent());
        assertEquals(id, response.getBody().get().getId());
    }

    @Test
    void getFlashCardsByUserId_shouldReturnList() {
        // given
        String userId = "user123";
        List<FlashCard> flashCards = Arrays.asList(new FlashCard(), new FlashCard());

        when(flashCardService.getAllFlashCardsByUserId(userId)).thenReturn(flashCards);

        // when
        ResponseEntity<List<FlashCard>> response = flashCardController.getFlashCardsByUserId(userId);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
    }

    @Test
    void getFlashCardsByUserId_shouldReturnNoContentWhenEmpty() {
        // given
        String userId = "user123";

        when(flashCardService.getAllFlashCardsByUserId(userId)).thenReturn(Collections.emptyList());

        // when
        ResponseEntity<List<FlashCard>> response = flashCardController.getFlashCardsByUserId(userId);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateFlashCard_shouldReturnUpdatedFlashCard() {
        // given
        String id = "123";
        FlashCardUpdateRequest request = new FlashCardUpdateRequest();
        FlashCard updatedCard = new FlashCard();
        updatedCard.setId(id);

        when(flashCardService.updateFlashCard(id, request)).thenReturn(updatedCard);

        // when
        ResponseEntity<FlashCard> response = flashCardController.updateFlashCard(id, request);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(id, response.getBody().getId());
    }

    @Test
    void deleteFlashCard_shouldReturnNoContent() {
        // given
        String id = "123";

        doNothing().when(flashCardService).deleteFlashCard(id);

        // when
        ResponseEntity<Void> response = flashCardController.deleteFlashCard(id);

        // then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(flashCardService, times(1)).deleteFlashCard(id);
    }
}
