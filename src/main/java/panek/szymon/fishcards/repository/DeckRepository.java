package panek.szymon.fishcards.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import panek.szymon.fishcards.entity.Deck;

@Repository
public interface DeckRepository extends MongoRepository<Deck, String> {

}
