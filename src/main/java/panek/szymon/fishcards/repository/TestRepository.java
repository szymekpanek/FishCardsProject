package panek.szymon.fishcards.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import panek.szymon.fishcards.entity.Test;
@Repository
public interface TestRepository extends MongoRepository<Test, String> {
}
