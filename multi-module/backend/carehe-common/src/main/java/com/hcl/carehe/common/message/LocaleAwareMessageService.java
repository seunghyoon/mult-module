package com.hcl.carehe.common.message;

import java.util.Locale;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class LocaleAwareMessageService {

    @Autowired
	private MessageSource messageSource;

	public String getMessage(String code){
        return messageSource.getMessage(code, null, LocaleContextHolder.getLocale());
    }
	
	public String getMessage(String code, Locale lang){
		if(ObjectUtils.isEmpty(lang)) {
			lang = LocaleContextHolder.getLocale();
		}
        return messageSource.getMessage(code, null, lang);
    }
	
	public String getMessage(String code, Object[] messageDatas, Locale lang){
		log.debug("LocaleAwareMessageService.getMessage code => {} , messageDatas => {} , lang => {}", code, messageDatas, lang);
		if(ObjectUtils.isEmpty(lang)) {
			lang = LocaleContextHolder.getLocale();
		}
		
		log.debug("LocaleAwareMessageService.getMessage lang => {}", lang);

        return messageSource.getMessage(code, messageDatas, lang);
    }
}
