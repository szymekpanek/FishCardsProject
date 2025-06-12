package panek.szymon.fishcards.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "decks")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Deck {
    @Id
    private String id;
    private String name;
    private String userId;
    private List<String> flashCardIds;
}
