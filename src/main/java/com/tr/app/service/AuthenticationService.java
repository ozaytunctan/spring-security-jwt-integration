package com.tr.app.service;

import com.tr.app.dto.LoggedInUserDto;

public interface AuthenticationService {

	LoggedInUserDto authenticate(String authorization);

}
