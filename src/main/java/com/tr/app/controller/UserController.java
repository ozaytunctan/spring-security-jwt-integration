package com.tr.app.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tr.app.dto.CreateUserDto;
import com.tr.app.entity.User;
import com.tr.app.security.annotation.IdGuard;
import com.tr.app.service.UserService;

@RestController
@RequestMapping(path = "/rest/api/v1/user")
public class UserController {

	@Autowired
	private UserService userService;

	@PostMapping(path = "/register")
	public ResponseEntity<?> register(@RequestBody() @Valid CreateUserDto dto) {
		return ResponseEntity//
				.status(HttpStatus.CREATED)//
				.body(this.userService.register(dto));
	}

	
	@IdGuard(parameterIndex = 0)
	@GetMapping(path = "/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {
		return ResponseEntity.ok(this.userService.getUserById(id));
	}

}
