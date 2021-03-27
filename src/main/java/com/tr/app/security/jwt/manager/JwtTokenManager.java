package com.tr.app.security.jwt.manager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenManager {

	private static final String AUTH_HEADER_PREFIX = "Bearer ";

	private static final String AUTH_HEADER_KEY = HttpHeaders.AUTHORIZATION;

	@Value("${application.jwt.token.expiration.secret-key}")
	private String secretKey;

	@Value("${application.jwt.token.expiration.duration-in-ms}")
	private Long expirationDurationInMs;

	@Autowired
	private EncryptionManager encryptionManager;

	public String generateToken(UserDetails userDetails, Long userId) {

		Map<String, Object> claims = new HashMap<>();

		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

		if (roles.contains(new SimpleGrantedAuthority("ADMIN"))) {
			claims.put("isAdmin", true);
		}

		if (roles.contains(new SimpleGrantedAuthority("USER"))) {
			claims.put("isUser", true);
		}

		if (userId != null && userId > 0) {
			claims.put("ticket", encryptionManager.encrypt(String.valueOf(userId)));
		}

		return doGenerateToken(claims, userDetails.getUsername());

	}

	private String doGenerateToken(Map<String, Object> claims, String subject) {
		long currentTimeMillis = System.currentTimeMillis();
		return Jwts.builder()//
				.setClaims(claims)//
				.setSubject(subject)//
				.setIssuedAt(new Date(currentTimeMillis))//
				.setExpiration(new Date(currentTimeMillis + expirationDurationInMs))//
				.signWith(SignatureAlgorithm.HS256, secretKey).compact();

	}

	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	// retrieve ticket from jwt token
	public String getTicketFromToken(String token) {
		final Claims claims = getAllClaimsFromToken(token);
		return claims.get("ticket", String.class);
	}

	public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
		
		Claims claims = getAllClaimsFromToken(token);
		List<SimpleGrantedAuthority> roles = new ArrayList<SimpleGrantedAuthority>(2);
		
		Boolean admin = claims.get("isAdmin", Boolean.class);
		if (admin != null && admin) {
			roles.add(new SimpleGrantedAuthority("ADMIN"));
		}

		Boolean user = claims.get("isUser", Boolean.class);
		if (user != null && user) {
			roles.add(new SimpleGrantedAuthority("USER"));
		}
		
		return roles;
	
}
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()//
				.setSigningKey(secretKey)//
				.parseClaimsJws(token)//
				.getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	// validate token
	public Boolean validateToken(String token) {
		final String username = getUsernameFromToken(token);
		return (username!=null && !isTokenExpired(token));
	}
	
	public String getHeaderToken(HttpServletRequest request) {
		final String requestTokenHeader = request.getHeader(AUTH_HEADER_KEY);
		if (requestTokenHeader != null && requestTokenHeader.startsWith(AUTH_HEADER_PREFIX))
			return requestTokenHeader.substring(AUTH_HEADER_PREFIX.length());
		
		return null;
	}

	

}
