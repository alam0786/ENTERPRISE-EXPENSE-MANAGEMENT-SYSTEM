package in.zidiolearning.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.zidiolearning.Entity.OAuthDetails;
import in.zidiolearning.Entity.Role;
import in.zidiolearning.Entity.User;
import in.zidiolearning.Repository.OAuthDetailsRepository;
import in.zidiolearning.Repository.RoleRepository;
import in.zidiolearning.Repository.UserRepository;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private RoleRepository roleRepository;
    @Autowired private OAuthDetailsRepository oauthDetailsRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void assignRole(Long userId, String roleName) {
        User user = userRepository.findById(userId).orElseThrow();
        Role role = roleRepository.findByName(roleName).orElseThrow();
        user.getRoles().add(role);
        userRepository.save(user);
    }
    

    public User registerOAuthUser(String provider, String providerId, String name, String email, String roleName) {
        Optional<User> existingUser = userRepository.findByEmail(email);
        if (existingUser.isPresent()) return existingUser.get();

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setActive(true);
        user.setPassword("");  // No password for OAuth

        Role role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.setRoles(Set.of(role));

        user = userRepository.save(user);

        OAuthDetails oauthDetails = new OAuthDetails();
        oauthDetails.setProvider(provider);
        oauthDetails.setProviderId(providerId);
        oauthDetails.setUser(user);

        oauthDetailsRepository.save(oauthDetails);

        return user;
    }
}
