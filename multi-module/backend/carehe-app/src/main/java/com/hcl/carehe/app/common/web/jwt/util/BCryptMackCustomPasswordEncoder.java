package com.hcl.carehe.app.common.web.jwt.util;

import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BCryptMackCustomPasswordEncoder implements PasswordEncoder {
	
	@SuppressWarnings("unused")
	private Pattern BCRYPT_PATTERN = Pattern.compile("\\A\\$2(a|y|b)?\\$(\\d\\d)\\$[./0-9A-Za-z]{53}");
	
	@Override
	public String encode(CharSequence rawPassword) {
		String plainTextPassword = rawPassword.toString();
		return BCrypt.hashpw(plainTextPassword.getBytes(), BCrypt.gensalt());
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		String plainTextPassword = rawPassword.toString();
		log.debug("BCryptMackCustomPasswordEncoder.matches => {}/{}", plainTextPassword, encodedPassword);
		return BCrypt.checkpw(plainTextPassword, encodedPassword);
	}
	
	public static void main(String[] args) {
		//log.debug("BCryptMackCustomPasswordEncoder.matches => {}/{}", BCrypt.hashpw("hclnew1234!".getBytes(), BCrypt.gensalt()));
		System.out.println(BCrypt.hashpw("hclnew1234!".getBytes(), BCrypt.gensalt()) + "-------"+BCrypt.gensalt());
		//$2a$10$/jX7CCVQaXrOXleypNq.jO
	}
}
