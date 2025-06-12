package panek.szymon.fishcards.dto;

import lombok.Data;
import panek.szymon.fishcards.entity.Enum.Role;

import java.util.Set;
@Data
public class UserRegisterDTO {
    private String username;
    private String password;
    private Set<Role> roles;
}
