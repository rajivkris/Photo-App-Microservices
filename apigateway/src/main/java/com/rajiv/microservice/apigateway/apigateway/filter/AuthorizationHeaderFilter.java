package com.rajiv.microservice.apigateway.apigateway.filter;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config>{
	
	public static class Config {
		
	}
	
	public AuthorizationHeaderFilter() {
		super(Config.class);
	}
	
	@Autowired
	private Environment env;

	@Override
	public GatewayFilter apply(Config config) {
		return (ServerWebExchange exchange, GatewayFilterChain chain) -> {
			ServerHttpRequest req = exchange.getRequest();
			
			if (!req.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
				return onError(exchange, "Request doesnot have Authorization header", HttpStatus.UNAUTHORIZED);
			}
			
			final String authHeader = req.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
			final String token = authHeader.replace("Bearer", "").replace(" ", "");
			if (!isJwtValid(token)) {
				return onError(exchange, "Not a valid autorization token", HttpStatus.UNAUTHORIZED);
			}
			
			return chain.filter(exchange);
			
		};
	}
	
	private Mono<Void> onError(ServerWebExchange exchange, String msg, HttpStatus status) {
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(status);
		return response.setComplete();
	}
	
	private boolean isJwtValid(String token) {
		
		final String subject = Jwts.parser()
			.setSigningKey(env.getProperty("secret.key"))
			.parseClaimsJws(token)
			.getBody()
			.getSubject();
		
		if (StringUtils.isBlank(subject)) {
			return false;
		}
		
		return true;
	}

}
