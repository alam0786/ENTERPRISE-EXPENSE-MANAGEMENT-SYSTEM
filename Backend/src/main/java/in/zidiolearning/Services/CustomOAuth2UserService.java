package in.zidiolearning.Services;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import in.zidiolearning.Entity.Role;
import in.zidiolearning.Entity.User;
import in.zidiolearning.Repository.RoleRepository;
import in.zidiolearning.Repository.UserRepository;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService
{

	private final UserRepository userRepository;
	
	private final RoleRepository roleRepository;
	
	public CustomOAuth2UserService(UserRepository userRepository,RoleRepository roleRepository) {
		this.userRepository=userRepository;
		this.roleRepository=roleRepository;
	}
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
		OAuth2User oAuth2User=super.loadUser(userRequest);
		String email=oAuth2User.getAttribute("email");
		User user=userRepository.findByEmail(email).orElseGet(()->{
			User newUser=new User();
			newUser.setUsername(email);
			newUser.setEmail(email);
			newUser.setPassword("N/A");
			Role userRole=roleRepository.findByName("EMPLOYEE")
					.orElseThrow(()->new RuntimeException("role not fond"));
			newUser.setRoles(Set.of(userRole));
			newUser.setEnabled(true);
			return userRepository.save(newUser);
		});
		
		return new DefaultOAuth2User(
				user.getRoles().stream()
				.map(role->new SimpleGrantedAuthority("ROLE"+role.getName()))
				.collect(Collectors.toList()), 
				oAuth2User.getAttributes(), "email");
	}
	
}
