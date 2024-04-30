package com.hcl.carehe.common.utils;


import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.hcl.carehe.common.Const;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpServletUtil {
	
	
	public static String getClientIpAddress(){
		HttpServletRequest request = getHttpServletRequest();
		String ip = request.getHeader("X-FORWARDED-FOR");
		
		if(ip == null){			
			ip = request.getHeader("Proxy-Client-IP");
		}
		if(ip == null){			
			ip = request.getHeader("NS-CLIENT-IP");
		}
		if(ip == null){			
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if(ip == null){
			ip = request.getRemoteAddr();
		}
		return ip;
	}
	
	public static String getTransationId(){
		String transationId = "";
		try{
			transationId = (String) RequestContextHolder.getRequestAttributes().getAttribute(Const.TRANSATION_ID, RequestAttributes.SCOPE_REQUEST);
    	}catch(Exception e){
    		//log.trace(e.getMessage(), e);
    	}
		return transationId;
	}
	
	public static void setTransationId(){
		try{
			RequestContextHolder.getRequestAttributes().setAttribute(Const.TRANSATION_ID, UUID.randomUUID().toString(), RequestAttributes.SCOPE_REQUEST);
    	}catch(Exception e){
    		//log.trace(e.getMessage(), e);
    	}
	}
	
	
	/*
	public static GenericMessage getTransationGenericMessage(){
		GenericMessage messages = null;
		try{
			messages = (GenericMessage) RequestContextHolder.getRequestAttributes().getAttribute(Const.GENERIC_MESSAGE, RequestAttributes.SCOPE_REQUEST);
    	}catch(Exception e){
    		//log.trace(e.getMessage(), e);
    	}
		return messages;
	}
	*/
	
	@SuppressWarnings("unchecked")
	public static Map<String, Object>  getUserInfo(){
		Map<String, Object> userInfo = null;
		try{
			userInfo = (Map<String, Object>) RequestContextHolder.getRequestAttributes().getAttribute(Const.USER_SESSION_INFO, RequestAttributes.SCOPE_REQUEST);
    	}catch(Exception e){
    		//log.trace(e.getMessage(), e);
    	}
		return userInfo;
	}
	
	
	public static String getQueryString(){
		String queryString = "";
		try{
			queryString = getHttpServletRequest().getQueryString();
    	}catch(Exception e){
    		//log.trace(e.getMessage(), e);
    	}
		return queryString;
	}
	
	public static String getContextPath(){
		String contextPath = "";
		try{
			contextPath = getHttpServletRequest().getContextPath();
    	}catch(Exception e){
    		//log.trace(e.getMessage(), e);
    	}
		return contextPath;
	}
	public static String getRequestUrI(){
		String url = "";
		try{
			url = getHttpServletRequest().getRequestURL().toString().replaceFirst(getHttpServletRequest().getContextPath(), "");
    	}catch(Exception e){
    		//log.trace(e.getMessage(), e);
    	}
		return url;
	}
	
	public static HttpServletRequest getHttpServletRequest(){
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static String getHeader(String headerKey){
		String headerData = "";
		try{
			log.debug("getHttpServletRequest() = > {}", getHttpServletRequest());
			headerData = getHttpServletRequest().getHeader(headerKey);
    	}catch(Exception e){
    		log.error(e.getMessage(), e);
    	}
		return headerData;
		
	}
	
	public static String getUserAgent(){
		String userAgent = "";
		try{
			userAgent = getHttpServletRequest().getHeader("user-agent");
    	}catch(Exception e){
    		log.trace(e.getMessage(), e);
    	}
		return userAgent;
		
	}
	public static Locale getUserLang(){ 
		Locale userLang = null; 
		try{ 
			
			userLang = Locale.of(getHttpServletRequest().getHeader("user-lang"));
		}catch(Exception e){ 
			log.trace(e.getMessage(), e);
		} 
		return userLang;
	}
	
	public static Integer getHttpStatusCode(){
		return (Integer) getHttpServletRequest().getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
	}
	public static Integer getHttpStatusCode(String errorStatusCode){
		return (Integer) getHttpServletRequest().getAttribute(errorStatusCode);
	}
	public static String getRequestForwardUri(){
		
		return (String) getHttpServletRequest().getAttribute(RequestDispatcher.FORWARD_REQUEST_URI);
	}
	
	
	public static HttpSession getHttpSession(){
		HttpSession session = null;
		try{
			session = getHttpServletRequest().getSession();
    	}catch(Exception e){
    		log.trace(e.getMessage(), e);
    	}
		return session;
	}
	
}
