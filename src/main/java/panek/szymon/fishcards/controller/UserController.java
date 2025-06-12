package panek.szymon.fishcards.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import panek.szymon.fishcards.controller.interfaces.UserControllerInterface;
import panek.szymon.fishcards.dto.UserDTO;
import panek.szymon.fishcards.dto.UserRegisterDTO;
import panek.szymon.fishcards.service.interfaces.UserServiceInterface;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController implements UserControllerInterface {
    private final UserServiceInterface userService;

    @Override
    public ResponseEntity<UserDTO> getUserById(String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @Override
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@RequestBody UserRegisterDTO userRegisterDTO) {
        return ResponseEntity.ok(userService.createUser(userRegisterDTO));
    }

    @Override
    public ResponseEntity<UserDTO> updateUser(String id, UserDTO userDTO) {
        return ResponseEntity.ok(userService.updateUser(id, userDTO));
    }

    @Override
    public ResponseEntity<Void> deleteUser(String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
