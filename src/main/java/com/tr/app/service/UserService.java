package com.tr.app.service;

import com.tr.app.dto.CreateUserDto;
import com.tr.app.entity.User;

public interface UserService {

	User getByEmail(String email); 
	
	CreateUserDto register(CreateUserDto dto);

	User getUserById(Long id);
}
