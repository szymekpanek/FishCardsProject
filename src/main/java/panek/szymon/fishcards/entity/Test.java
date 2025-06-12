package panek.szymon.fishcards.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import panek.szymon.fishcards.dto.TestQuestionDTO;
import panek.szymon.fishcards.entity.Enum.TestType;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "tests")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    private String id;
    private String userId;
    private TestType type;
    private LocalDateTime createdAt;
    private List<TestQuestionDTO> questions;
}

