package panek.szymon.fishcards.service.interfaces;

import panek.szymon.fishcards.dto.UserDTO;
import panek.szymon.fishcards.dto.UserRegisterDTO;

import java.util.List;

public interface UserServiceInterface {
    UserDTO getUserById(String id);
    List<UserDTO> getAllUsers();
    UserDTO createUser(UserRegisterDTO userRegisterDTO);
    UserDTO updateUser(String id, UserDTO userDTO);
    void deleteUser(String id);
}

