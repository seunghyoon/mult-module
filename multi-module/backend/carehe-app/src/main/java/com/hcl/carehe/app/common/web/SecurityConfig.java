package com.hcl.carehe.app.common.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import com.hcl.carehe.app.common.web.jwt.JwtAccessDeniedHandler;
import com.hcl.carehe.app.common.web.jwt.JwtAuthenticationEntryPoint;
import com.hcl.carehe.app.common.web.jwt.JwtSecurityConfig;
import com.hcl.carehe.app.common.web.jwt.TokenProvider;
import com.hcl.carehe.app.common.web.jwt.util.BCryptMackCustomPasswordEncoder;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {
	
	
    private final CorsFilter corsFilter;
	private final TokenProvider tokenProvider;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    
    @Bean
    PasswordEncoder passwordEncoder() {
    	log.debug("SecurityConfig.passwordEncoder() ");
    	return new BCryptMackCustomPasswordEncoder();
    }
	public SecurityConfig(CorsFilter corsFilter,
						  TokenProvider tokenProvider,
						  JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
						  JwtAccessDeniedHandler jwtAccessDeniedHandler) {
		
		 this.tokenProvider = tokenProvider;
	     this.corsFilter = corsFilter;
	     this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
	     this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
	}
	
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	log.debug("SecurityConfig.filterChain()  {} ",http);
    	http
			.csrf(AbstractHttpConfigurer::disable)
    		.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
    		.exceptionHandling(exceptionHandlingCustomizer -> exceptionHandlingCustomizer
    															.authenticationEntryPoint(jwtAuthenticationEntryPoint)
    															.accessDeniedHandler(jwtAccessDeniedHandler))
    		.headers(headersCustomizer -> headersCustomizer.frameOptions(frameOptionsCustomizer -> frameOptionsCustomizer.sameOrigin()))
    	    .sessionManagement(sessionManagementCustomizer -> sessionManagementCustomizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    		.authorizeHttpRequests(authorizeHttpRequestsCustomizer -> authorizeHttpRequestsCustomizer
					/*API URL 정의시 contextPath 제외하거 넣을것*/
					.requestMatchers("/carehe/v1.0/certification/login",
									 "/carehe/v1.0/group/*/code/info", 
									 "/v1.0/cm/system/info",
									 "/v1.0/cm/rsa/public/key/info/system/*",
									 "/favicon.ico"
					).permitAll()
					.anyRequest()
					.authenticated())
    	    .with(new JwtSecurityConfig(tokenProvider), customizer ->{});
        return http.build();
    }
}
