package panek.szymon.fishcards.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import panek.szymon.fishcards.entity.FlashCard;
@Repository
public interface FlashCardRepository extends MongoRepository<FlashCard, String> {
}
