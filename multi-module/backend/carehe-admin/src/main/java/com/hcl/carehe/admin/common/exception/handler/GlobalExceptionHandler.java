package com.hcl.carehe.admin.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.carehe.common.message.GenericMessage;


@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler({BadCredentialsException.class})
	@ResponseStatus(HttpStatus.OK) // 200
	public GenericMessage handleSecurityCheckException(GenericMessage messages, 
			Exception exception) {
		if(exception instanceof BadCredentialsException) {
			BadCredentialsException badCredentialsException = (BadCredentialsException) exception;
			messages.setFAIL();
			messages.setBizResCode("SECURITY-ERROR-00000000001");
			messages.setMessageDatas(new Object[] {badCredentialsException.getMessage()});
		}
		messages.setHttpStatusCode(HttpStatus.OK.value());
		return messages;
	}
	
	@ExceptionHandler({InternalAuthenticationServiceException.class})
	@ResponseStatus(HttpStatus.OK) // 200
	public GenericMessage handleCheckException(GenericMessage messages, 
			Exception exception) {
		messages.setBizResCode("SECURITY-ERROR-00000000001");
		messages.setFAIL();
		messages.setHttpStatusCode(HttpStatus.OK.value());
		return messages;
	}
}