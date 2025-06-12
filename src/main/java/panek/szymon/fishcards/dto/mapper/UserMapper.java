package panek.szymon.fishcards.dto.mapper;

import org.mapstruct.Mapper;
import panek.szymon.fishcards.dto.UserDTO;
import panek.szymon.fishcards.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDTO toDTO(User user);
    User toEntity(UserDTO dto);
}

