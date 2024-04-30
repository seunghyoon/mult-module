package com.hcl.carehe.common.utils;

public class StringUtil {
	
	public static boolean isEmptStringArray(String[] array) {
		return array == null || array.length < 1;
	}
	
	public static String getStringBufferAppend(String ... args) {
		if(isEmptStringArray(args)) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < args.length; i++) {
			sb.append(args[i]);
		}
		return sb.toString();
	}
}
