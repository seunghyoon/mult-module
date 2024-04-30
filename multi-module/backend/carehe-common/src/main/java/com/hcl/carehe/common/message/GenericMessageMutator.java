package com.hcl.carehe.common.message;

import java.util.Locale;

public interface GenericMessageMutator {
	
	public String getResponseCode();
	
	public boolean isCustomMessage();
	
	public String getMessage();

	public Object getData();

	public String getReturnCode();
	
    public String getBizResCode();
    
    public String getTransactionId();
    
    public String getMaskServerIp();

    public Object getMessageDatas();
    
    public Locale getLang();
    
	public void transformMessage(LocaleAwareMessageService service);
	
	public String getServerTime();
	
	public Integer getHttpStatusCode();

	

}
