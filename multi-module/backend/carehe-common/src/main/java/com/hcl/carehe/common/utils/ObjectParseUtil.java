package com.hcl.carehe.common.utils;

import java.util.Map;

import org.apache.commons.lang3.ObjectUtils;



public class ObjectParseUtil {
	
	@SuppressWarnings("unchecked")
	public static <T> T getMapToObject(
			Map<String, Object> mapObject, String key){
		try {
			if(ObjectUtils.isEmpty(mapObject)){
				return null;
			}else{
				return (T) mapObject.get(key);
			}
		} catch (Exception e) {
			//throw new JsonParseException(e);
		}
		return null;
	}
}
