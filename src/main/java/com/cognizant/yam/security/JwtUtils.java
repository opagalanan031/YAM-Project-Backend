package com.cognizant.yam.security;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtUtils {
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${com.cognizant.yam.jwtSecret}")
	private String jwtSecret;
	@Value("${com.cognizant.yam.jwtExpMs}")
	private Long jwtExpirationMs;
	
	public String generateToken(Authentication auth) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) auth.getPrincipal();
		
		return Jwts.builder().setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(SignatureAlgorithm.HS512, jwtSecret)
				.compact();
	}
	
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch(ExpiredJwtException e) {
			LOGGER.error("JWT token expired: {}", e.getMessage());
		} catch(UnsupportedJwtException e) {
			LOGGER.error("JWT token is unsupported: {}", e.getMessage());
		} catch(MalformedJwtException e) {
			LOGGER.error("Invalid JWT token: {}", e.getMessage());
		} catch(SignatureException e) {
			LOGGER.error("Invalid JWT token signature: {}", e.getMessage());
		} catch(IllegalArgumentException e) {
			LOGGER.error("Invalid argument: {}", e.getMessage());
		}
		
		return false;
	}
	
	public String getUsernameFromJwtToken(String authToken) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken)
				.getBody().getSubject();
	}
}
