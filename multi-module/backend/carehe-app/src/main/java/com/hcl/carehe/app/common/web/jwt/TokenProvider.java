package com.hcl.carehe.app.common.web.jwt;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenProvider implements InitializingBean{
	
	private static final String AUTHORITIES_KEY = "auth";

	@Value("${jwt.access-token-secret}")
	private String accessTokenSecret;

	@Value("${jwt.token-validity-in-milli-seconds}")
	private Long tokenValidityInMilliseconds;

	@Value("${jwt.refresh-token-validity-in-milli-seconds}")
	private Long refreshTokenValidityInMilliseconds;
	
	private Key accessTokenKey;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("TokenProvider.afterPropertiesSet secret => {} ", accessTokenSecret);
		this.accessTokenKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessTokenSecret));
	}
	
	/*
	 * key 생성
	 * 
	 */
	public Key getTokenKey(String tokenSecret) {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(tokenSecret));
	}
	
	
	/*
	 * jwt Token 생성
	 * 
	 */
	public String createToken(Authentication authentication, Key tokenKey) {
		log.debug("TokenProvider.createToken authorities => {} ", authentication.getName());

		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority :: getAuthority)
				.collect(Collectors.joining(","));
		log.debug("TokenProvider.createToken authorities => {} ", authorities);
		log.debug("TokenProvider.createToken tokenKey => {} ", tokenKey);
		log.debug("TokenProvider.createToken accessTokenKey => {} ", accessTokenKey);

		if(ObjectUtils.isEmpty(tokenKey)){
			tokenKey = accessTokenKey;
		}
		/*
		String createTokenData = Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(tokenKey, SignatureAlgorithm.HS512)
				.setExpiration(java.sql.Timestamp.valueOf(LocalDateTime.now().plus(tokenValidityInMilliseconds, ChronoUnit.MILLIS)))
				.compact();
		*/
		String createTokenData = Jwts.builder()
	            .subject(authentication.getName())
	            .claim(AUTHORITIES_KEY, authorities)
	            .signWith((SecretKey) tokenKey, Jwts.SIG.HS512)
	            .expiration(java.sql.Timestamp.valueOf(LocalDateTime.now().plus(tokenValidityInMilliseconds, ChronoUnit.MILLIS)))
	            .compact();

		log.debug("TokenProvider.createToken createTokenData => {} ", createTokenData);

		return createTokenData;
	}

	/**
	 * refresh token 생성
	 */
	public String createRefreshToken(Authentication authentication, Key tokenKey) {
		log.debug("TokenProvider.createToken authorities => {} ", authentication.getName());
		/*
		String authorities = authentication.getAuthorities().stream()
				.map(GrantedAuthority :: getAuthority)
				.collect(Collectors.joining(","));
		log.debug("TokenProvider.createRefreshToken authorities => {} ", authorities);
		log.debug("TokenProvider.createRefreshToken tokenKey => {} ", tokenKey);
		log.debug("TokenProvider.createRefreshToken accessTokenKey => {} ", accessTokenKey);

		String createTokenData = Jwts.builder()
				.setSubject(authentication.getName())
				.claim(AUTHORITIES_KEY, authorities)
				.signWith(tokenKey, SignatureAlgorithm.HS512)
				.setExpiration(java.sql.Timestamp.valueOf(LocalDateTime.now().plus(refreshTokenValidityInMilliseconds, ChronoUnit.MILLIS)))
				.compact();
		*/
		String createTokenData = createToken(authentication, tokenKey);
		
		log.debug("TokenProvider.createRefreshToken createTokenData => {} ", createTokenData);

		return createTokenData;
	}

	/*
	 * jwt Token 권한정보 return 
	 */
	public Authentication getAuthentication(String token, Key tokenKey) {
		log.debug("TokenProvider.getAuthentication token => {} ", token);
		if(ObjectUtils.isEmpty(tokenKey)){
			tokenKey = accessTokenKey;
		}
		/*
		Claims claims = Jwts.parserBuilder().setSigningKey(tokenKey)
				.build().parseClaimsJws(token)
				.getBody();
		*/
		Claims claims = Jwts
                .parser()
                .verifyWith((SecretKey) tokenKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
		
		log.debug("TokenProvider.getAuthentication claims => {} ", claims);

		Collection<? extends GrantedAuthority> authorities = 
				Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
					.map(SimpleGrantedAuthority :: new)
					.collect(Collectors.toList());
		log.debug("TokenProvider.getAuthentication authorities => {} ", authorities);

		User principal = new User(claims.getSubject(), "", authorities);
		log.debug("TokenProvider.getAuthentication principal => {} ", principal);
		
		log.debug("TokenProvider.getAuthentication principal => {} token = > {},authorities = > {} ", principal, token, authorities);


		return new UsernamePasswordAuthenticationToken(principal, token, authorities);
	}
	
	/*
	 * jwt Token validateToken
	 */
	public Boolean validateToken(String token, Key tokenKey) {
		log.debug("TokenProvider.validateToken token => {} ", token);
		if(ObjectUtils.isEmpty(tokenKey)){
			tokenKey = accessTokenKey;
		}
		try {
			Jwts.parser().verifyWith((SecretKey) tokenKey).build().parseSignedClaims(token);
			//Jwts.parserBuilder().setSigningKey(tokenKey).build().parseClaimsJws(token);
			log.debug("TokenProvider.validateToken validateToken => {} ", "정상");
			return true;
		}catch(SecurityException | MalformedJwtException e) {
			log.debug("TokenProvider.validateToken validateToken => {} ", "잘못된 JWT 서명입니다");
			log.error(e.getMessage(), e);
		}catch(ExpiredJwtException e) {
			log.debug("TokenProvider.validateToken validateToken => {} ", "만료된 JWT 토큰입니다.");
			log.error(e.getMessage(), e);
		}catch(UnsupportedJwtException e) {
			log.debug("TokenProvider.validateToken validateToken => {} ", "지원되지 않는 JWT 토큰입니다.");
			log.error(e.getMessage(), e);
		}catch(IllegalArgumentException e) {
			log.debug("TokenProvider.validateToken validateToken => {} ", "JWT 토큰이 잘못되었습니다.");
			log.error(e.getMessage(), e);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		return false;
	}
}
