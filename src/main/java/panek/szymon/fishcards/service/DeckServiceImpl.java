package panek.szymon.fishcards.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import panek.szymon.fishcards.dto.DeckUpdateRequest;
import panek.szymon.fishcards.dto.mapper.DeckMapper;
import panek.szymon.fishcards.entity.Deck;
import panek.szymon.fishcards.exceptions.DeckNotFoundException;
import panek.szymon.fishcards.repository.DeckRepository;
import panek.szymon.fishcards.service.interfaces.DeckService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeckServiceImpl implements DeckService {

    private final DeckRepository deckRepository;
    private final DeckMapper deckMapper;

    @Override
    public Deck updateDeck(String id, DeckUpdateRequest request) {
        log.info("Updating Deck with id: {}", id);
        Deck existingDeck = getDeck(id);
        deckMapper.updateDeckFromDto(request, existingDeck);
        log.info("Deck with id {} updated successfully", id);
        return deckRepository.save(existingDeck);
    }

    @Override
    public Optional<Deck> getDeckById(String id) {
        log.info("Fetching deck with id: {}", id);
        return deckRepository.findById(id);
    }

    @Override
    public List<Deck> getAllDecksByUserId(String userId) {
        log.info("Fetching Decks for userId: {}", userId);
        return deckRepository.findAllByUserId(userId);
    }

    @Override
    public Deck createDeck(Deck deck) {
        log.info("Creating new deck with id: {}", deck.getId());
        return deckRepository.save(deck);
    }

    @Override
    public void deleteDeck(String id) {
        log.info("Deleting deck with id: {}", id);
        Deck existingDeck = getDeck(id);
        deckRepository.delete(existingDeck);
        log.info("Deck with id {} deleted successfully", id);
    }

    private Deck getDeck(String id) {
        return deckRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Deck with id {} not found for update", id);
                    return new DeckNotFoundException("FlashCard with id " + id + " not found");
                });
    }
}
