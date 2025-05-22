package in.zidiolearning.Services;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.zidiolearning.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService
{

	private final UserRepository userRepository;
	
	public CustomUserDetailsService(UserRepository userRepository) {
		this.userRepository=userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		in.zidiolearning.Entity.User user=userRepository.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("user not found"+username));
		
		
		return new org.springframework.security.core.userdetails.User(
			user.getUsername(),
			user.getPassword(),
			user.isEnabled(),
			true,
			true,
			true,
			user.getRoles().stream()
				.map(role->new SimpleGrantedAuthority("ROLE"+role.getName()))
				.collect(Collectors.toList())
			
		);
	}

	
	
}
