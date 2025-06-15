package panek.szymon.fishcards.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import panek.szymon.fishcards.dto.FlashCardUpdateRequest;
import panek.szymon.fishcards.dto.mapper.FlashCardMapper;
import panek.szymon.fishcards.entity.FlashCard;
import panek.szymon.fishcards.exceptions.FlashCardNotFoundException;
import panek.szymon.fishcards.repository.FlashCardRepository;
import panek.szymon.fishcards.service.interfaces.FlashCardService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class FlashCardServiceImpl implements FlashCardService {
    private final FlashCardRepository flashCardRepository;
    private final FlashCardMapper flashCardMapper;

    @Override
    public Optional<FlashCard> getFlashCardById(String id) {
        log.info("Fetching flashcard with id: {}", id);
        return flashCardRepository.findById(id);
    }

    @Override
    public List<FlashCard> getAllFlashCards() {
        log.info("Fetching all flashcards");
        return flashCardRepository.findAll();
    }

    @Override
    public FlashCard createFlashCard(FlashCard flashCard) {
        log.info("Creating new flashcard with id: {}", flashCard.getId());
        return flashCardRepository.save(flashCard);
    }

    @Override
    public FlashCard updateFlashCard(String id, FlashCardUpdateRequest request) {
        log.info("Updating flashcard with id: {}", id);
        FlashCard existingFlashCard = flashCardRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("FlashCard with id {} not found for update", id);
                    return new FlashCardNotFoundException("FlashCard with id " + id + " not found");
                });

        flashCardMapper.updateFlashCardFromDto(request, existingFlashCard);
        log.info("Flashcard with id {} updated successfully", id);
        return flashCardRepository.save(existingFlashCard);
    }

    @Override
    public void deleteFlashCard(String id) {
        log.info("Deleting flashcard with id: {}", id);
        FlashCard existingFlashCard = flashCardRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("FlashCard with id {} not found for deletion", id);
                    return new FlashCardNotFoundException("FlashCard with id " + id + " not found");
                });

        flashCardRepository.delete(existingFlashCard);
        log.info("Flashcard with id {} deleted successfully", id);
    }

    @Override
    public List<FlashCard> getAllFlashCardsByUserId(String userId) {
        log.info("Fetching flashcards for userId: {}", userId);
        return flashCardRepository.findAllByUserId(userId);
    }


}
