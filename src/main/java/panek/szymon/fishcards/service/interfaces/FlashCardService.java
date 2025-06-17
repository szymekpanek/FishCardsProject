package panek.szymon.fishcards.service.interfaces;

import panek.szymon.fishcards.dto.FlashCardCreateRequest;
import panek.szymon.fishcards.dto.FlashCardUpdateRequest;
import panek.szymon.fishcards.entity.FlashCard;

import java.util.List;
import java.util.Optional;

public interface FlashCardService {
    Optional<FlashCard> getFlashCardById(String id);

    List<FlashCard> getAllFlashCards();

    FlashCard createFlashCard(FlashCard flashCard);

    FlashCard updateFlashCard(String id, FlashCardUpdateRequest request);

    void deleteFlashCard(String id);

    List<FlashCard> getAllFlashCardsByUserId(String userId);
    List<FlashCard> createMultipleFlashCardsFromDto(List<FlashCardCreateRequest> requests);
}
