package panek.szymon.fishcards.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlashCardCreateRequest {
    private String question;
    private String answer;
    private String userId;
}
