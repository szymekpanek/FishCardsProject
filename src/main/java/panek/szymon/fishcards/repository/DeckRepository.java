package panek.szymon.fishcards.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import panek.szymon.fishcards.entity.Deck;
import panek.szymon.fishcards.entity.FlashCard;

import java.util.List;

@Repository
public interface DeckRepository extends MongoRepository<Deck, String> {
    List<Deck> findAllByUserId(String userId);

}
