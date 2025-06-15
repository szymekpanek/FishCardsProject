package panek.szymon.fishcards.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import panek.szymon.fishcards.entity.User;
import panek.szymon.fishcards.repository.UserRepository;
import panek.szymon.fishcards.service.interfaces.UserService;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public User getUserById(String id) {
        log.info("Fetching user with id: {}", id);
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        log.info("Fetching all users");
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        log.info("Creating user with email: {}", user.getEmail());
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.warn("Attempt to create user with existing email: {}", user.getEmail());
            throw new RuntimeException("User with this email already exists.");
        }
        return userRepository.save(user);
    }

    @Override
    public User updateUser(String id, User user) {
        log.info("Updating user with id: {}", id);
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());

        return userRepository.save(existingUser);
    }

    @Override
    public void deleteUser(String id) {
        log.info("Deleting user with id: {}", id);
        userRepository.deleteById(id);
    }
}
