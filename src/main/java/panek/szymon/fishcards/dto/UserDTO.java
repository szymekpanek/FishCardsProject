package panek.szymon.fishcards.dto;

import lombok.Data;
import panek.szymon.fishcards.entity.Enum.Role;

import java.util.Set;

@Data
public class UserDTO {
    private String id;
    private String username;
    private Set<Role> roles;
}

