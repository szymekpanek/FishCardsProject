package panek.szymon.fishcards.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import panek.szymon.fishcards.entity.User;
import panek.szymon.fishcards.repository.UserRepository;


import java.io.IOException;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        if (authentication instanceof OAuth2AuthenticationToken) {
            OAuth2User oAuth2User = ((OAuth2AuthenticationToken) authentication).getPrincipal();
            Map<String, Object> attributes = oAuth2User.getAttributes();

            String email = (String) attributes.get("email");
            String name = (String) attributes.get("name");
            String familyName = (String) attributes.get("family_name");
            String givenName = (String) attributes.get("given_name");

            if (email == null || name == null) {
                throw new IllegalArgumentException("Brak wymaganych danych użytkownika!");
            }

            userRepository.findByEmail(email).ifPresentOrElse(
                    user -> System.out.println("Użytkownik już istnieje: " + user.getEmail()),
                    () -> {
                        User newUser = new User();
                        newUser.setEmail(email);
                        newUser.setUserName(name);
                        newUser.setFamily_name(familyName);
                        newUser.setGiven_name(givenName);
                        userRepository.save(newUser);
                        System.out.println(">>> Nowy użytkownik zapisany: " + email);
                    }
            );
        }

        response.sendRedirect("/api/user/me");
    }
}
