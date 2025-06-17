package panek.szymon.fishcards.service.interfaces;

import panek.szymon.fishcards.dto.DeckUpdateRequest;
import panek.szymon.fishcards.entity.Deck;

import java.util.List;
import java.util.Optional;

public interface DeckService {

    Deck updateDeck(String id, DeckUpdateRequest request);

    Optional<Deck> getDeckById(String id);

    List<Deck> getAllDecksByUserId(String userId);

    Deck createDeck(Deck deck);

    void deleteDeck(String id);
}
