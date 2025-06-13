package panek.szymon.fishcards.service.interfaces;

import panek.szymon.fishcards.entity.User;

import java.util.List;

public interface UserServiceInterface {
    User getUserById(String id);

    List<User> getAllUsers();

    User createUser(User user);

    User updateUser(String id, User userDTO);

    void deleteUser(String id);
}

