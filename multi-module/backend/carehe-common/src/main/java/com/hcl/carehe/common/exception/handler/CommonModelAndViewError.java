package com.hcl.carehe.common.exception.handler;

public class CommonModelAndViewError {
	
	public CommonModelAndViewError() {}
	
	public CommonModelAndViewError(String code, String message, String moreInfo) {
		this.code = code;
		this.message = message;
		this.moreInfo = moreInfo;
	}
	
	private String code;
	
	private String message;
	
	private String moreInfo;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}
	
	
}
