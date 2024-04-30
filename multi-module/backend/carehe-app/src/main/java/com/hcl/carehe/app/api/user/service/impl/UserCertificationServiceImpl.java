package com.hcl.carehe.app.api.user.service.impl;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;

import com.hcl.carehe.app.api.user.entity.CustomUserDetailEntity;
import com.hcl.carehe.app.api.user.mapper.UserCertificationMapper;
import com.hcl.carehe.app.api.user.service.UserCertificationService;
import com.hcl.carehe.app.common.web.jwt.JwtFilter;
import com.hcl.carehe.app.common.web.jwt.TokenProvider;
import com.hcl.carehe.common.utils.JsonParseUtil;
import com.hcl.carehe.common.utils.ObjectParseUtil;
import com.hcl.carehe.common.utils.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserCertificationServiceImpl implements UserCertificationService, UserDetailsService {
	
	
	private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
	
	public UserCertificationServiceImpl(TokenProvider tokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.tokenProvider = tokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }
	
	
	@Autowired
	private UserCertificationMapper userCertificationMapper;
	
	@Value("${jwt.refresh-token-secret}")
	private String refreshTokenSecret;

	@Value("${jwt.token-validity-in-milli-seconds}")
	private Long tokenValidityInMilliseconds;

	
	@Override
	public Map<String, Object> getUserLogInAuthorizeTokenService(Map<String, Object> requestInfo) {
		log.debug("UserCertificationServiceImpl.getUserLogInAuthorizeTokenService requestInfo => {} ", requestInfo);
		Map<String, Object> resData = new HashMap<>();
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(ObjectParseUtil.getMapToObject(requestInfo, "id"), ObjectParseUtil.getMapToObject(requestInfo, "pwd"));
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		
		log.debug("UserCertificationServiceImpl.getUserLogInAuthorizeTokenService authentication => {} ", authentication);
		
		String accessToken = tokenProvider.createToken(authentication, null);
		log.debug("UserServiceController.getUserLogAuthorizeTokenService() accessToken = > {} ", accessToken);
		String refreshToken = tokenProvider.createToken(authentication, tokenProvider.getTokenKey(refreshTokenSecret));
		log.debug("UserServiceController.getUserLogAuthorizeTokenService() refreshToken = > {} ", refreshToken);
		resData.put("accessToken", StringUtil.getStringBufferAppend(JwtFilter.BEARER_DATA, accessToken));
		resData.put("refreshToken", refreshToken);

		return resData;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("UserCertificationServiceImpl.loadUserByUsername username => {} ", username);
		Map<String, Object> userInfo = userCertificationMapper.findUserByUserId(username);
		log.debug("UserCertificationServiceImpl.loadUserByUsername userInfo => {} ", userInfo);

		return CustomUserDetailEntity
                .builder()
                .username(JsonParseUtil.getObjectToStringData(getBaseTokenData(userInfo)))
                .password(ObjectParseUtil.getMapToObject(userInfo, "pwd"))
                .enabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .authorities(null).build();
	}
	
	public Map<String, Object> getBaseTokenData(Map<String, Object> userInfo){
		Map<String, Object> baseTokenData = new HashMap<>();
		baseTokenData.put("accId", ObjectParseUtil.getMapToObject(userInfo, "acc_id"));
		baseTokenData.put("userId", ObjectParseUtil.getMapToObject(userInfo, "user_id"));
		baseTokenData.put("id", ObjectParseUtil.getMapToObject(userInfo, "id"));
		return baseTokenData;
	}
}
