package in.zidiolearning.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import in.zidiolearning.Entity.User;
import in.zidiolearning.Repository.UserRepository;
import in.zidiolearning.utils.JwtUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication)
                                        throws IOException, ServletException {

        DefaultOAuth2User oauthUser = (DefaultOAuth2User) authentication.getPrincipal();

        // Extract email from OAuth2 attributes
        String email = (String) oauthUser.getAttributes().get("email");

        // Load existing user or create
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isEmpty()) {
            User user = new User();
            user.setEmail(email);
            user.setUsername((String) oauthUser.getAttributes().get("name"));
            user.setActive(true);
            userRepository.save(user);
        }

        // Generate JWT token
        String token = jwtUtil.generateToken(email);

        // Return JWT in response (JSON response)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), new JwtResponse(token));
    }

    // Simple DTO class for response
    static class JwtResponse {
        public String token;

        public JwtResponse(String token) {
            this.token = token;
        }
    }
}
