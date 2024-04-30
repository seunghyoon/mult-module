package com.hcl.carehe.common.exception;

public class FrameworkException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String code;
	
	private Object[] messages;
	
	private Exception cause;
	
	public FrameworkException() {
		super();
	}
	
	public FrameworkException(String code, Object[] messages, Exception cause) {
		this.setCode(code);
		this.setMessages(messages);
		this.setCause(cause);
	}
	
	public FrameworkException(String code, Exception cause) {
		this.setCode(code);
		this.setCause(cause);
	}
	
	public FrameworkException(String code, Object[] messages) {
		this.setCode(code);
		this.setMessages(messages);
	}
	
	public FrameworkException(Exception cause) {
		this.setCause(cause);
	}
	
	public FrameworkException(String code) {
		this.setCode(code);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Object[] getMessages() {
		return messages;
	}

	public void setMessages(Object[] messages) {
		this.messages = messages;
	}

	public Exception getCause() {
		return cause;
	}

	public void setCause(Exception cause) {
		this.cause = cause;
	}

}
