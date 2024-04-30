package com.hcl.carehe.common.exception;



public class BizCheckException extends BaseException{

	private static final long serialVersionUID = 2465437760933986790L;
	
	private String code;
	
	private Object[] messages;
	
	private Exception cause;
	
	public BizCheckException() {
		super();
	}
	
	public BizCheckException(String code, Object[] messages, Exception cause) {
		this.setCode(code);
		this.setMessages(messages);
		this.setCause(cause);
	}
	
	public BizCheckException(String code, Exception cause) {
		this.setCode(code);
		this.setCause(cause);
	}
	
	public BizCheckException(String code, Object[] messages) {
		this.setCode(code);
		this.setMessages(messages);
	}
	
	public BizCheckException(Exception cause) {
		this.setCause(cause);
	}
	
	public BizCheckException(String code) {
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
