package panek.szymon.fishcards.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import panek.szymon.fishcards.entity.User;
import panek.szymon.fishcards.repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = (String) attributes.get("email");
        String name = (String) attributes.get("name");

        if (email == null || name == null) {
            throw new IllegalArgumentException("Brak wymaganych danych użytkownika");
        }

        userRepository.findByEmail(email).ifPresentOrElse(
                user -> System.out.println("Użytkownik już istnieje: " + user.getEmail()),
                () -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setUserName(name);
                    userRepository.save(newUser);

                }
        );
        return oAuth2User;
    }
}
