package panek.szymon.fishcards.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import panek.szymon.fishcards.entity.Enum.Difficulty;

@Document(collection = "flashcards")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlashCard {
    @Id
    private String id;
    private String question;
    private String answer;
    private String topicId;
    private String deckId;
    private String userId;
    private Difficulty difficulty;
}

