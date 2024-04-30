package com.hcl.carehe.common.exception;

import java.util.Locale;

public class ResourceNotFoundException extends BaseException{

	private static final long serialVersionUID = -2511739733546408393L;
	
	
	public ResourceNotFoundException(String code, Object[] messages, Locale lang) {
		super(code, messages, lang);
	}

}