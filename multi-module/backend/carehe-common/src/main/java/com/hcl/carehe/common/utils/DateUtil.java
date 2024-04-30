package com.hcl.carehe.common.utils;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


public class DateUtil {
	public static final String PATTERN_SERVER_TIME_A = "yyyy.MM.dd HH:mm:ss.SSS";

	public static final String PATTERN_SERVER_TIME_B = "yyyy.MM.dd";

	public static final String PATTERN_SERVER_TIME_C = "yyyy.MM.dd HH";

	public static final String PATTERN_SERVER_TIME_D = "yyyy.MM.dd HH:mm";

	public static final String PATTERN_SERVER_TIME_E = "yyyy.MM.dd HH:mm:ss";
     


    public static final String PATTERN_SERVER_TIME_F = "yyyyMMddHHmmssSSS";

    public static final String PATTERN_SERVER_TIME_G = "yyyyMMdd";

    public static final String PATTERN_SERVER_TIME_H = "yyyyMMddHH";

    public static final String PATTERN_SERVER_TIME_I = "yyyyMMddHHmm";

    public static final String PATTERN_SERVER_TIME_J = "yyyyMMddHHmmss";

    public static final String PATTERN_SERVER_TIME_K = "yyyy.MM";
     
    public static final String PATTERN_SERVER_TIME_L = "yyyyMM";
    
    
    public static final String PATTERN_SERVER_TIME_SYSTEM_INFO_01 = "MM월 dd일 (E) a HH:mm";
    public static final String PATTERN_SERVER_TIME_SYSTEM_INFO_02 = "MM월 dd일 (E) a HH시";
     
	public static String getNowLocalDateTime(String forPattern){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(forPattern);

		return LocalDateTime.now().format(formatter);
	}
     
	public static String getNowLocalDatePlusMinutesTime(String forPattern, long months){

    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern(forPattern);

    	return LocalDateTime.now().plusMinutes(months).format(formatter);
	}
	
	public static LocalDateTime getParseLocalDateTime(String textTime, String forPattern){

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(forPattern);

    	return LocalDateTime.parse(textTime, formatter);
	}
	public static List<String[]> getRangeTime(String startTextTime, String startFormat, 
			String endTextTime, String endFormat){
		if(StringUtils.isEmpty(startTextTime)
				|| StringUtils.isEmpty(startFormat)
				|| StringUtils.isEmpty(endTextTime)
				|| StringUtils.isEmpty(endFormat)) {
			return null;
		}
		
		LocalDateTime startTime = getParseLocalDateTime(startTextTime, startFormat);
		LocalDateTime endTime =   getParseLocalDateTime(endTextTime, endFormat);
		LocalDateTime curTime = LocalDateTime.now().minusMinutes(1);
		if(startTime.isBefore(curTime)) {
			if(endTime.isAfter(curTime)) {
				endTime = curTime;
			}
			List<String[]> rangeTimeList = new ArrayList<>();
			while(!startTime.isAfter(endTime)
					&& !startTime.isAfter(LocalDateTime.now())) {
				
				String criteria = startTime.format(DateTimeFormatter.ofPattern(PATTERN_SERVER_TIME_I));
				
				String start = StringUtil.getStringBufferAppend(criteria, "00000");
				
				String end = StringUtil.getStringBufferAppend(criteria, "59999");
				String[] rangeTime = {start, end};
				rangeTimeList.add(rangeTime);
				startTime = startTime.plusMinutes(1);
			}
			return rangeTimeList;
		}
    	return null;
	}
	
	
	public static Timestamp getLocalDateTimeToTimestamp(){
    	return Timestamp.valueOf(LocalDateTime.now());
	}
}
