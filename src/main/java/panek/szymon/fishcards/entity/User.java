package panek.szymon.fishcards.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import panek.szymon.fishcards.entity.Enum.Role;

import java.util.List;
import java.util.Set;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    private String id;
    private String userName;
    private String family_name;
    private String given_name;

    @Indexed(unique = true)
    private String email;

    private List<String> deckIds;
    private Set<Role> roles;
}

