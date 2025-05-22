package in.zidiolearning.JwtFilter;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider
{

	private final String jwtSecret="YourSecretKeyChangeMe!";
	
	private final long jwtExpirationMs=86400000;
	
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+jwtExpirationMs))
				.signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256)
				.compact();
				
	}
	
	public String getUsernameFromjwt(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8)))
				.build()
				.parseClaimsJws(authToken);
			return true;
			
		} catch (Exception e) {
			
		}
		return false;
	}
	
}
