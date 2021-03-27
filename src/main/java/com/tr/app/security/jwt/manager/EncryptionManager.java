package com.tr.app.security.jwt.manager;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class EncryptionManager {

	private final String SALT_KEY = "SECRETKEY+ENCRYPT123";

	public String encrypt(String encryptText) {
		if (!StringUtils.hasText(encryptText))
			return "";

		return DigestUtils.sha256Hex(encryptText + SALT_KEY);
	}
}
