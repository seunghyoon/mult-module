package com.hcl.carehe.common.exception;

import java.util.Locale;

public class BedRequestException extends BaseException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8649023314234075550L;

	public BedRequestException(String code, Object[] messages, Locale lang) {
		super(code, messages, lang);
	}

}