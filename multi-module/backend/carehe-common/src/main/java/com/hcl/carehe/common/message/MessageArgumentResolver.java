package com.hcl.carehe.common.message;


import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.hcl.carehe.common.Const;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Component
public class MessageArgumentResolver implements HandlerMethodArgumentResolver {


    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return GenericMessage.class.isAssignableFrom(parameter.getParameterType());
    }

    public MessageArgumentResolver() {}

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer container,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        GenericMessage messages = new RestMessage();
        messages.setSUCCESS();

        RequestContextHolder.getRequestAttributes().setAttribute(Const.GENERIC_MESSAGE, messages, RequestAttributes.SCOPE_REQUEST);
        log.info("MessageArgumentResolver.resolveArgument() messages => {}", messages);
        
        
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		log.debug("MessageArgumentResolver.resolveArgument() getName => {} ", authentication.getName());
		if(!"anonymousUser".equals(authentication.getName())) {
			Map<String, Object> user = JsonParseUtil.getStringChangObjectData(authentication.getName(), new HashMap<String, Object>());
			log.debug("MessageArgumentResolver.resolveArgument() user => {} ", user);
			RequestContextHolder.getRequestAttributes().setAttribute(Const.USER_SESSION_INFO, user, RequestAttributes.SCOPE_REQUEST);
		}
        */
        return messages;
    }
}
