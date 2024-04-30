package com.hcl.carehe.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hcl.carehe.common.utils.HttpServletUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class WebInterceptor implements HandlerInterceptor {


	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
			Exception exception) throws Exception {
		log.debug("WebInterceptor.afterCompletion()");
		
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
			ModelAndView exception) throws Exception {
		log.debug("WebInterceptor.postHandle()");

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object exception)
			throws Exception {

		HttpServletUtil.setTransationId();
		log.debug("WebInterceptor.preHandle() contextPath => {} " , HttpServletUtil.getContextPath());
		log.debug("WebInterceptor.preHandle() uri => {} " , HttpServletUtil.getRequestUrI());
		log.debug("WebInterceptor.preHandle() queryString => {} " , HttpServletUtil.getQueryString());
		log.debug("WebInterceptor.preHandle() transationId => {} " , HttpServletUtil.getTransationId());

		return true;
	}

}
