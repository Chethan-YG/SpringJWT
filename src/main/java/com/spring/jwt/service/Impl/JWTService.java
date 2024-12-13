package com.spring.jwt.service.Impl;


import java.util.Map;
import org.springframework.security.core.userdetails.UserDetails;
import io.jsonwebtoken.Claims;

public interface JWTService {
	
	String generateToken(UserDetails userDetails);
	String extractUserName(String token);
	boolean isTokenValid(String token, UserDetails userDetails);
	boolean isTokenExpired(String token);
	Claims extractAllClaims(String token);
	String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails);

}
