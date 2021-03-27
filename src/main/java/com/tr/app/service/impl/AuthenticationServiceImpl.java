package com.tr.app.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.tr.app.dto.LoggedInUserDto;
import com.tr.app.security.jwt.manager.JwtTokenManager;
import com.tr.app.service.AuthenticationService;
import com.tr.app.service.UserService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenManager tokenManager;


	/**
	 * 
	 */
	public LoggedInUserDto authenticate(String authorization) {

		if (!StringUtils.hasText(authorization)) {
			throw new RuntimeException("INVALID AUTH PAYLOAD");
		}

		String[] httpBasicAuthPayload = parseHttpBasicPayload(authorization);
		String email = httpBasicAuthPayload[0];
		String password = httpBasicAuthPayload[1];

		Authentication authenticate = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(email, password));

		User user = (User) authenticate.getPrincipal();
		if (user == null) {
			throw new RuntimeException("USER NOT FOUND");
		}

		com.tr.app.entity.User userEntity = userService.getByEmail(email);

		String jwtToken = tokenManager.generateToken(user, userEntity.getId());

		return new LoggedInUserDto(userEntity.getId(), jwtToken);
	}

	private String[] parseHttpBasicPayload(String authorization) {

		if (authorization != null && authorization.toLowerCase().startsWith("basic")) {
			String base64Credentials = authorization.substring("Basic".length()).trim();
			byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
			String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			return credentials.split(":", 2);
		}
		return null;
	}
}
