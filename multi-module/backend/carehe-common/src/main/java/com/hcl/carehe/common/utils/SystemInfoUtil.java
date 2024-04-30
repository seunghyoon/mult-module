package com.hcl.carehe.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SystemInfoUtil {
	
	public static final String SERVER_INFO_LOCAL = "LOCAL";
	public static final String SERVER_INFO_DEV = "DEV";
	public static final String SERVER_INFO_TESTBED = "TESTBED";
	public static final String SERVER_INFO_PRODUCTION = "PRODUCTION";
	
	private static final Object cacheLock = new Object();
	
	private static String serverIpAddress = null;
	
	public static String getHostAddress(){
		try {
			if(serverIpAddress == null) {
				synchronized(cacheLock){
					serverIpAddress = InetAddress.getLocalHost().getHostAddress();
				}
			}
			
			
		} catch (UnknownHostException e) {
            log.trace("SystemInfoUtil.getHostAddress error : {}", e);
		}
		return serverIpAddress;
	}
	public static String getHostAddressMasking(){
    	String hostAddressMasking = "";
        try {
        	String hostAddress = getHostAddress();
            if(!"".equals(hostAddress) && hostAddress.lastIndexOf(".") > -1) {
            	hostAddressMasking = hostAddress.replaceAll("(?>.{"+hostAddress.lastIndexOf(".")+"})." , "#");
            	//hostAddressMasking = hostAddress.replaceAll("(?>.{"+Integer.valueOf(hostAddress.lastIndexOf("."))+"})." , "#");//beanny
            	//hostAddressMasking = hostAddress.replaceAll("(?>.{"+new Integer(hostAddress.lastIndexOf(".")).toString()+"})." , "#");
            }
        } catch (Exception e) {
            //log.trace("SystemInfoUtil.getHostAddressMasking error : {}", e);
        }
        return hostAddressMasking;
	}
}
