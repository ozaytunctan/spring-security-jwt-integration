package com.tr.app.service.impl;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.tr.app.dto.CreateUserDto;
import com.tr.app.entity.User;
import com.tr.app.repository.UserRepository;
import com.tr.app.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User getByEmail(String email) {
		return userRepository.findByEmail(email)//
				.orElseThrow(() -> new EntityNotFoundException("User not found by email:" + email));
	}

	@Override
	public CreateUserDto register(CreateUserDto dto) {

		dto.setPassword(passwordEncoder.encode(dto.getPassword()));

		User userEntity = new User();
		userEntity.setEmail(dto.getEmail());
		userEntity.setPassword(dto.getPassword());
		userEntity.setRole(dto.getRole());

		userEntity = userRepository.save(userEntity);

		dto.setId(userEntity.getId());

		return dto;
	}

	@Override
	public User getUserById(Long id) {
		// TODO Auto-generated method stub
		return this.userRepository.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("User not found by id:" + id));
	}

}
