package com.tom.front.authbasic.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import com.tom.front.authbasic.user.repository.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

	private final TokenRepository tokenRepository;

	@Override
	public void logout(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Authentication authentication) {
		final String authHeader = request.getHeader("Authorization");
		
		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			return;
		}

		final String jwt = authHeader.substring(7);
		var storedToken = tokenRepository.findByToken(jwt);

		storedToken.ifPresent(token -> {
			token.revoke();
			token.expire();
			tokenRepository.save(token);
		});
	}
	
}
