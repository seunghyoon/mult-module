package com.hcl.carehe.common.exception;

import java.util.Locale;

public class UnAuthorizedException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8367362152696547061L;

	public UnAuthorizedException(String code, Object[] messages, Locale lang) {
		super(code, messages, lang);
	}

}