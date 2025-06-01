package in.zidiolearning.service;


import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import in.zidiolearning.Entity.Role;
import in.zidiolearning.Entity.User;
import in.zidiolearning.Repository.UserRepository;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) {
        OAuth2User oAuth2User = super.loadUser(request);
        String provider = request.getClientRegistration().getRegistrationId(); // "google" or "github"
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String email = switch (provider) {
            case "google" -> (String) attributes.get("email");
            case "github" -> (String) attributes.get("login") + "@github.com";
            default -> throw new RuntimeException("Unsupported provider: " + provider);
        };

        userRepository.findByEmail(email).orElseGet(() -> {
            User user = User.builder()
                    .email(email)
                    .username(provider + "_" + attributes.get("name"))
                    .role(Role.ROLE_EMPLOYEE)
                    .provider(provider)
                    .build();
            return userRepository.save(user);
        });

        return oAuth2User;
    }
}
