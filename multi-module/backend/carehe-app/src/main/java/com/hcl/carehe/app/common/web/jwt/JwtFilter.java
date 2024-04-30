package com.hcl.carehe.app.common.web.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JwtFilter extends GenericFilterBean {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String BEARER_DATA = "Bearer ";
	public static final Integer BEARER_DATA_LENGTH = BEARER_DATA.length();
	
	 
	
	private TokenProvider tokenProvider;
	
	public JwtFilter(TokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, 
			FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
		String jwt = resolveToken(httpServletRequest.getHeader(AUTHORIZATION_HEADER));
		String requestURI = httpServletRequest.getRequestURI();
		
		
		log.debug("JwtFilter.doFilter()  jwt => {}", jwt);

		if (StringUtils.hasText(jwt) 
				&& tokenProvider.validateToken(jwt, null)) {
			Authentication authentication = tokenProvider.getAuthentication(jwt, null);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			log.debug("JwtFilter.doFilter authentication2 => {} / {} ", authentication.getName(), requestURI);
		} else {
			log.debug("JwtFilter.doFilter 유효한 JWT 토큰이 없습니다, uri => {} ", requestURI);
		}

		filterChain.doFilter(servletRequest, servletResponse);
	}

	public static String resolveToken(String bearerToken) {

		if (StringUtils.hasText(bearerToken) 
				&& bearerToken.startsWith(BEARER_DATA)) {
			return bearerToken.substring(BEARER_DATA_LENGTH);
		}
		return null;
   }
}
