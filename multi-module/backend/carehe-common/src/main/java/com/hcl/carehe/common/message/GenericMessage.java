package com.hcl.carehe.common.message;

import java.util.Locale;

public interface GenericMessage {
	
    public static final String SUCCESS = "SUCCESS";

    public static final String FAIL = "FAIL";
    
    public void setResponseCode(String responseCode);
    
    public void setSUCCESS();

    public void setFAIL();

    public void enableCustomMessage();

    public void setMessage(String message);

    public void setData(Object data);

    public void setReturnCode(String returnCode);
    
    public void setBizResCode(String bizResCode);
    
    public void setTransactionId(String transactionId);
    
    public void setMaskServerIp(String maskServerIp);
    
    public void setMessageDatas(Object[] messageDatas);
    
    public void setLang(Locale lang);
    
    public void setServerTime(String serverTime);
    
    public void setHttpStatusCode(Integer httpStatusCode);
    
}