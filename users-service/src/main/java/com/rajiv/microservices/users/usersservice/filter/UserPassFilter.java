package com.rajiv.microservices.users.usersservice.filter;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajiv.microservices.users.usersservice.request.model.LoginModel;

public class UserPassFilter extends UsernamePasswordAuthenticationFilter {

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {
		
		try {
			final LoginModel model = new ObjectMapper().readValue(req.getInputStream(), LoginModel.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(model.getEmail(),
							model.getPassword(),
							new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
}
