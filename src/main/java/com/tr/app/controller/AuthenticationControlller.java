package com.tr.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.app.dto.LoggedInUserDto;
import com.tr.app.service.AuthenticationService;

@RestController
@RequestMapping(path = "/rest/api/v1/auth")
public class AuthenticationControlller {

	@Autowired
	private AuthenticationService authenticationService;

	/**
	 * Basic Authentication
	 * {@link https://www.debugbear.com/basic-auth-header-generator}
	 * 
	 * @param authorization
	 * @return
	 */
	@PostMapping(path = "/login")
	public ResponseEntity<LoggedInUserDto> login(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {

		try {
			LoggedInUserDto response = authenticationService.authenticate(authorization);
			return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, response.getToken()).body(response);

		} catch (BadCredentialsException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}

}
