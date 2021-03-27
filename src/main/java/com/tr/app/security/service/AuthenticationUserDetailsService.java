package com.tr.app.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

 
import com.tr.app.service.UserService;

@Component
public class AuthenticationUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		com.tr.app.entity.User userEntity = userService.getByEmail(email);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found!");
		}
		 return User.withUsername(userEntity.getEmail())//
                .password(userEntity.getPassword())//
                .authorities("USER")//
                .build();
	}
}
