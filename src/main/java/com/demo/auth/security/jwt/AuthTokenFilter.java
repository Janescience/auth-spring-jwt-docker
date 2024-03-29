package com.demo.auth.security.jwt;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.demo.auth.entity.app.RefreshToken;
import com.demo.auth.security.services.RefreshTokenService;
import com.demo.auth.security.services.UserDetailsServiceImpl;
import com.demo.auth.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.util.Optional;

@Log4j2
@Component
public class AuthTokenFilter extends OncePerRequestFilter{

    @Autowired
	private JwtUtil jwtUtil;

    @Autowired
	private UserDetailsServiceImpl userDetailsService;

	@Autowired
	private RefreshTokenService refreshTokenService;

    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = parseJwt(request);

			String rft = parseRft(request);
			Optional<RefreshToken> refreshTokenOpt = refreshTokenService.findByToken(rft);
			
			RefreshToken refreshToken = null;
			if(refreshTokenOpt.isPresent()){
				refreshToken = refreshTokenService.verifyExpiration(refreshTokenOpt.get());
			}

			if ((jwt != null && jwtUtil.validateJwtToken(jwt)) && refreshToken != null) {
				String username = jwtUtil.getUserNameFromJwtToken(jwt);

				UserDetails userDetails = userDetailsService.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.error("Cannot set user authentication: {}", e);
		}

		filterChain.doFilter(request, response);
	}

    private String parseJwt(HttpServletRequest request) {
		String headerAuth = request.getHeader("Authorization");
		if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
			return headerAuth.substring(7, headerAuth.length());
		}

		return null;
	}

	private String parseRft(HttpServletRequest request) {
		String headerRft = request.getHeader("RefreshToken");
		if (StringUtils.hasText(headerRft)) {
			return headerRft;
		}

		return null;
	}
    
}
