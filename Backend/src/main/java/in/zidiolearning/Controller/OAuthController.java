package in.zidiolearning.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.zidiolearning.Dto.AuthResponse;
import in.zidiolearning.Dto.OAuthRequest;
import in.zidiolearning.Entity.User;
import in.zidiolearning.service.UserService;
import in.zidiolearning.utils.JwtUtil;

@RestController
@RequestMapping("/api/oauth")
public class OAuthController {

    @Autowired private UserService userService;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> oauthLogin(@RequestBody OAuthRequest request) {
        User user = userService.registerOAuthUser(
                request.provider, request.providerId, request.name, request.email, request.role);
        String token = jwtUtil.generateToken(user.getEmail());

        return ResponseEntity.ok(new AuthResponse("OAuth Login Successful", token, user.getUsername(), user.getEmail(), request.role));
    }
}
