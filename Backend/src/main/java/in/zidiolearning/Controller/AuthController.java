package in.zidiolearning.Controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.zidiolearning.Dto.RegisterRequest;
import in.zidiolearning.Entity.Role;
import in.zidiolearning.Entity.User;
import in.zidiolearning.JwtFilter.JwtTokenProvider;
import in.zidiolearning.Repository.RoleRepository;
import in.zidiolearning.Repository.UserRepository;
import jakarta.validation.Valid;
import in.zidiolearning.Dto.JwtAuthenticationResponse;
import in.zidiolearning.Dto.LoginRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController 
{

	@Autowired
	private final AuthenticationManager authenticationManager;
	
	@Autowired
	private final UserRepository userRepository;
	
	@Autowired
	private final RoleRepository roleRepository;
	
	@Autowired
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	private final JwtTokenProvider jwtTokenProvider;
	
	public AuthController(AuthenticationManager authenticationManager,
			UserRepository userRepository,RoleRepository roleRepository,
			PasswordEncoder passwordEncoder,JwtTokenProvider jwtTokenProvider) {
		this.authenticationManager=authenticationManager;
		this.userRepository=userRepository;
		this.roleRepository=roleRepository;
		this.passwordEncoder=passwordEncoder;
		this.jwtTokenProvider=jwtTokenProvider;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){
		if(userRepository.existsByUsername(registerRequest.getUsername())) {
			return ResponseEntity.badRequest().body("user is already taken");
		}
		if(userRepository.existsByEmail(registerRequest.getEmail())) {
			return ResponseEntity.badRequest().body("email is already use");
		}
		
		User user=new User();
		user.setUsername(registerRequest.getUsername());
		user.setEmail(registerRequest.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
		
		Role userRole=roleRepository.findByName(registerRequest.getRole())
				.orElseThrow(()->new RuntimeException("Role Not Found"));
		user.setRoles(Set.of(userRole));
		user.setEnabled(true);
		userRepository.save(user);
		return ResponseEntity.ok("User Register Successfuly");
	}
	
	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest ){
		Authentication authentication=authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt=jwtTokenProvider.generateToken((UserDetails) authentication.getPrincipal());
		return ResponseEntity.ok(new JwtAuthenticationResponse(jwt));
	}
}
