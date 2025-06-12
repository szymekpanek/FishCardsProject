package panek.szymon.fishcards.controller.interfaces;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import panek.szymon.fishcards.dto.UserDTO;
import panek.szymon.fishcards.dto.UserRegisterDTO;

import java.util.List;

public interface UserControllerInterface {
    ResponseEntity<UserDTO> getUserById(String id);
    ResponseEntity<List<UserDTO>> getAllUsers();
    ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterDTO userRegisterDTO);
    ResponseEntity<UserDTO> updateUser(String id, UserDTO userDTO);
    ResponseEntity<Void> deleteUser(String id);
}

