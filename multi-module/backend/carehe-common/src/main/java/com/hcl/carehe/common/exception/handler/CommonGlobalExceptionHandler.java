package com.hcl.carehe.common.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.hcl.carehe.common.exception.BedRequestException;
import com.hcl.carehe.common.exception.BizCheckException;
import com.hcl.carehe.common.exception.NoContentException;
import com.hcl.carehe.common.message.GenericMessage;
import com.hcl.carehe.common.message.RestMessage;
import com.hcl.carehe.common.utils.HttpServletUtil;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
@Slf4j
public class CommonGlobalExceptionHandler {

	@ExceptionHandler({NoResourceFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public GenericMessage handleResourceNotFoundException(GenericMessage messages, NoResourceFoundException exception) {
		messages.setFAIL();
		messages.setBizResCode("HTTP.ERROR.404");
		messages.setMessageDatas(new Object[] {});
		messages.setHttpStatusCode(HttpStatus.NOT_FOUND.value());
		return messages;
	}
	
	@ExceptionHandler(BedRequestException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public GenericMessage handleBedRequestException(GenericMessage messages, BedRequestException exception) {
		messages.setFAIL();
		messages.setBizResCode(exception.getCode());
		messages.setMessageDatas(exception.getMessages());
		messages.setHttpStatusCode(HttpStatus.BAD_REQUEST.value());
		return messages;
	}
	
	@ExceptionHandler(NoContentException.class)
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public GenericMessage handleNoContentException(GenericMessage messages, NoContentException exception) {
		messages.setFAIL();
		messages.setBizResCode(exception.getCode());
		messages.setMessageDatas(exception.getMessages());
		messages.setHttpStatusCode(HttpStatus.NO_CONTENT.value());
		return messages;
	}
	@ExceptionHandler({BizCheckException.class})
	@ResponseStatus(HttpStatus.OK) // 200
	public GenericMessage handleCheckException(GenericMessage messages, 
			Exception exception) {
		BizCheckException bizException = (BizCheckException)exception;
		messages.setFAIL();
		messages.setBizResCode(bizException.getCode());
		messages.setMessageDatas(bizException.getMessages());
		messages.setHttpStatusCode(HttpStatus.OK.value());
		return messages;
	}
	/**/
	@ExceptionHandler({Exception.class})
	@ResponseStatus(HttpStatus.OK) // 500
	public GenericMessage handleException(GenericMessage messages, Exception exception) {
		if(messages == null) {
			messages = new RestMessage();
		}
		
		String reqUrl = HttpServletUtil.getRequestUrI();
		String transationId = HttpServletUtil.getTransationId();
		log.error("GlobalExceptionHandler.handleException ERROR reqUrl =>  {}", reqUrl);
		log.error("GlobalExceptionHandler.handleException ERROR transationId =>  {}", transationId);
		
		log.error(exception.getMessage(), exception);
		log.error("GlobalExceptionHandler.handleException exception {} ", exception.getClass());

		messages.setBizResCode("SYSTEM-ERROR-00000000001");
		messages.setFAIL();
		messages.setMessageDatas(null);
		messages.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return messages;
	} 
}