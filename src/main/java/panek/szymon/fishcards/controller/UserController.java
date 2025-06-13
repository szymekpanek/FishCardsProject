package panek.szymon.fishcards.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import panek.szymon.fishcards.entity.User;
import panek.szymon.fishcards.service.interfaces.UserServiceInterface;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserServiceInterface userService;

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(@AuthenticationPrincipal OAuth2User oAuth2User) {
        System.out.println("=== Pobranie użytkownika z sesji ===");
        if (oAuth2User == null) {
            System.out.println("Brak zalogowanego użytkownika!");
            return Map.of("error", "Nie znaleziono użytkownika");
        }

        Map<String, Object> attributes = oAuth2User.getAttributes();
        attributes.forEach((key, value) -> System.out.println(key + ": " + value));

        return attributes;
    }


}
