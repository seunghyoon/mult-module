package com.hcl.carehe.common.exception;

import java.util.Locale;

public class BaseException extends RuntimeException{
	
	private static final long serialVersionUID = -3324817931455469171L;

	private String code;
	
	private Object[] messages;
	
	private Exception cause;
	
	private Locale lang;
	
	
	
	public Locale getLang() {
		return lang;
	}

	public void setLang(Locale lang) {
		this.lang = lang;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BaseException() {
		super();
	}
	
	public BaseException(String code, Object[] messages, Exception cause, Locale lang) {
		this.setCode(code);
		this.setMessages(messages);
		this.setCause(cause);
		this.setLang(lang);
	}
	
	public BaseException(String code, Exception cause, Locale lang) {
		this.setCode(code);
		this.setCause(cause);
		this.setLang(lang);
	}
	
	public BaseException(String code, Object[] messages, Locale lang) {
		this.setCode(code);
		this.setMessages(messages);
		this.setLang(lang);
	}
	
	public BaseException(Exception cause) {
		this.setCause(cause);
	}
	
	public BaseException(String code) {
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
