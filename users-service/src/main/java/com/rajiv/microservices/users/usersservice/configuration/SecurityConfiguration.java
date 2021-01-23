package com.rajiv.microservices.users.usersservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.rajiv.microservices.users.usersservice.filter.UserPassFilter;
import com.rajiv.microservices.users.usersservice.service.UserManagementService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserManagementService userService;
	
	@Autowired
	private BCryptPasswordEncoder bcryptPassEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.authorizeRequests().antMatchers("/**").hasIpAddress(env.getProperty("gateway.ip"))
		.and().addFilter(getAutheticationFilter());
		http.headers().frameOptions().disable();
	}
	
	private UserPassFilter getAutheticationFilter() {
		final UserPassFilter filter = new UserPassFilter();
		try {
			filter.setAuthenticationManager(authenticationManager());
			return filter;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	protected void configure(AuthenticationManagerBuilder aubuilder) throws Exception {
		aubuilder.userDetailsService(userService).passwordEncoder(bcryptPassEncoder);
	}
}
