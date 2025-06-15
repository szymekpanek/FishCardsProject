package panek.szymon.fishcards.dto;

import lombok.Data;
import panek.szymon.fishcards.entity.Enum.Difficulty;

@Data
public class FlashCardUpdateRequest {
    private String question;
    private String answer;
    private String topicId;
    private String deckId;
    private Difficulty difficulty;
}
