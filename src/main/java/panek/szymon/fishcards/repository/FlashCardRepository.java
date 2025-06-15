package panek.szymon.fishcards.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import panek.szymon.fishcards.entity.FlashCard;

import java.util.List;

@Repository
public interface FlashCardRepository extends MongoRepository<FlashCard, String> {
    List<FlashCard> findAllByUserId(String userId);
}
