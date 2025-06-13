package panek.szymon.fishcards.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import panek.szymon.fishcards.entity.User;
import panek.szymon.fishcards.repository.UserRepository;
import panek.szymon.fishcards.service.interfaces.UserServiceInterface;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserServiceInterface {
    private final UserRepository userRepository;


    @Override
    public User getUserById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists.");
        }
        return userRepository.save(user);
    }


    @Override
    public User updateUser(String id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        existingUser.setUserName(user.getUserName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRoles(user.getRoles());

        return userRepository.save(existingUser);
    }


    @Override
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }


}
