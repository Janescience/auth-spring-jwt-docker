package com.demo.auth.util;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.demo.auth.security.services.UserDetailsImpl;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class JwtUtil {
    
    @Value("${jwtSecret}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
	private int jwtExpirationMs;

    public String generateJwtToken(UserDetailsImpl  userPrincipal) {
		return generateTokenFromUsername(userPrincipal.getUsername());
	}

	public String generateTokenFromUsername(String username) {
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
			.setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
			.compact();
	  }

    public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
	}

    public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			log.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			log.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			log.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			log.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			log.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}
