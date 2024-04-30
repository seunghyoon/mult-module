package com.hcl.carehe.app.api.user.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carehe.app.api.user.service.UserCertificationService;
import com.hcl.carehe.app.common.web.jwt.JwtFilter;
import com.hcl.carehe.common.message.GenericMessage;
import com.hcl.carehe.common.utils.ObjectParseUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/carehe/v1.0")
public class UserCertificationController {
	
	@Value("${jwt.token-validity-in-milli-seconds}")
	private Long tokenValidityInMilliseconds;
	
	
	@Autowired
	private UserCertificationService userCertificationService;
	
	
	@PostMapping(value = {"/certification/login"})
	public GenericMessage login(GenericMessage message, 
			HttpServletResponse response,
			@RequestBody Map<String, Object> requestBody){
		log.debug("UserCertificationController.login requestBody => {} /tokenValidityInMilliseconds => {}", requestBody, tokenValidityInMilliseconds);
		Map<String, Object> resData = userCertificationService.getUserLogInAuthorizeTokenService(requestBody);
		String accessToken = ObjectParseUtil.getMapToObject(resData, "accessToken");
		message.setData(resData);
        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, accessToken);

        return message;
    }
	
	
}
