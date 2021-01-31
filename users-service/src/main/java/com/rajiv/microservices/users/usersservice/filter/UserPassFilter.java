package com.rajiv.microservices.users.usersservice.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rajiv.microservices.users.usersservice.dto.UserDTO;
import com.rajiv.microservices.users.usersservice.request.model.LoginModel;
import com.rajiv.microservices.users.usersservice.service.UserManagementService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class UserPassFilter extends UsernamePasswordAuthenticationFilter {

	private UserManagementService userService;

	private Environment env;

	@Autowired
	public UserPassFilter(UserManagementService userService, Environment env, AuthenticationManager authenticationManager) {
		this.userService = userService;
		this.env = env;
		this.setAuthenticationManager(authenticationManager);
		this.setFilterProcessesUrl("/users/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) {

		try {
			final LoginModel model = new ObjectMapper().readValue(req.getInputStream(), LoginModel.class);
			return getAuthenticationManager().authenticate(
					new UsernamePasswordAuthenticationToken(model.getEmail(), model.getPassword(), new ArrayList<>()));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String email = ((User) authResult.getPrincipal()).getUsername();
		final UserDTO dto = userService.fetchByUsername(email);

		final String token = Jwts.builder().setSubject(dto.getUserId())
				.setExpiration(new Date(System.currentTimeMillis() + Long.parseLong(env.getProperty("expiry.time"))))
				.signWith(SignatureAlgorithm.HS512, env.getProperty("secret.key")).compact();

		response.addHeader("token", token);
		response.addHeader("userid", dto.getUserId());

	}
}
