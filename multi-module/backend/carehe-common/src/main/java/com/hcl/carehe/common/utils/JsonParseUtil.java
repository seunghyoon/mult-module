package com.hcl.carehe.common.utils;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsonParseUtil {
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getStringChangMapData(String data){
		ObjectMapper mapper = null;
		try {
			if(StringUtils.isEmpty(data)){
				return null;
			}else{
				mapper = new ObjectMapper();
				return mapper.readValue(data, Map.class);
			}
		} catch (Exception e) {
			//throw new JsonParseException(e);
		}
		return null;
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T> T getStringChangObjectData(String data, Object obj){
		ObjectMapper mapper = null;
		try {
			if(StringUtils.isEmpty(data)){
				return null;
			}else{
				mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				return (T) mapper.readValue(data, obj.getClass());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public static String getObjectToStringData(Object data){
		ObjectMapper mapper = null;
		try {
			if(data == null){
				return null;
			}else{
				mapper = new ObjectMapper();
				mapper.setSerializationInclusion(Include.NON_NULL);
				return mapper.writeValueAsString(data);
			}
		} catch (Exception e) {
			//throw new JsonParseException(e);
		}
		return null;
	}
}
