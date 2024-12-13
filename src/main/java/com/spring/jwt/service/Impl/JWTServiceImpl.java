package com.spring.jwt.service.Impl;

import java.security.Key;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JWTServiceImpl implements JWTService{
	public String generateToken(UserDetails userDetails) {
		return Jwts.builder().setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60* 24))
				.signWith(getSigninKey(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	 public String extractUserName(String token) {
	        return extractClaim(token, Claims::getSubject);
	    }
	
	public Claims extractAllClaims(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }
	

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
	
	private Key getSigninKey()
	{
		byte[] key=Decoders.BASE64.decode("413F442847284862506553685660597033733676397924422645294840406351");
		return Keys.hmacShaKeyFor(key);
	}
	
	 public boolean isTokenValid(String token, UserDetails userDetails) {
	        final String userName = extractUserName(token);
	        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
	    }
	 
	    public boolean isTokenExpired(String token) {
	        return extractExpiration(token).before(new Date());
	    }

	    private Date extractExpiration(String token) {
	        return extractClaim(token, Claims::getExpiration);
	    }


		@Override
		public String generateRefreshToken(Map<String, Object> extractClaims, UserDetails userDetails) {
			return Jwts.builder().setClaims(extractClaims).setSubject(userDetails.getUsername())
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60* 24 * 7))
					.signWith(getSigninKey(),SignatureAlgorithm.HS256)
					.compact();
		}
}
