package com.hcl.carehe.common.exception;

import java.util.Locale;

public class NoContentException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1828414780335308272L;

	public NoContentException(String code, Object[] messages, Locale lang) {
		super(code, messages, lang);
	}

}
