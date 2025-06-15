package panek.szymon.fishcards.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import panek.szymon.fishcards.entity.Enum.Role;
import panek.szymon.fishcards.entity.User;
import panek.szymon.fishcards.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId("1");
        user.setUserName("John Doe");
        user.setEmail("john@example.com");
    }

    @Test
    void testGetUserById_UserExists() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        User result = userService.getUserById("1");

        assertEquals(user, result);
        verify(userRepository, times(1)).findById("1");
    }

    @Test
    void testGetUserById_UserNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.getUserById("1"));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testGetAllUsers() {
        List<User> users = List.of(user);
        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(user, result.getFirst());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testCreateUser_Success() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testCreateUser_EmailExists() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> userService.createUser(user));
        assertEquals("User with this email already exists.", exception.getMessage());
    }

    @Test
    void testUpdateUser_UserExists() {
        User updatedUser = new User();
        updatedUser.setUserName("New Name");
        updatedUser.setEmail("new@example.com");
        updatedUser.setRoles(Set.of(Role.USER));

        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User result = userService.updateUser("1", updatedUser);

        assertEquals("New Name", result.getUserName());
        assertEquals("new@example.com", result.getEmail());
        assertEquals(Set.of(Role.USER), result.getRoles());

        verify(userRepository, times(1)).findById("1");
        verify(userRepository, times(1)).save(user);
    }


    @Test
    void testUpdateUser_UserNotFound() {
        User updatedUser = new User();

        when(userRepository.findById("1")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class,
                () -> userService.updateUser("1", updatedUser));
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    void testDeleteUser() {
        doNothing().when(userRepository).deleteById("1");

        userService.deleteUser("1");

        verify(userRepository, times(1)).deleteById("1");
    }
}
