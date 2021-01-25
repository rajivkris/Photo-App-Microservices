package com.rajiv.microservice.apigateway.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import reactor.core.publisher.Mono;

@Configuration
public class GlobalFilterConfiguration {
	
	private final static Logger log = LoggerFactory.getLogger(GlobalFilterConfiguration.class);

	@Order(1)
	@Bean
	public GlobalFilter preAndPostFilter1() {
		return (exchange, chain) -> {
			log.info("I am in pre filter log of method 1");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("I am post filter from method 1");
			}));
		};
	}
	
	@Order(2)
	@Bean
	public GlobalFilter preAndPostFilter2() {
		return (exchange, chain) -> {
			log.info("I am in pre filter log of method 2");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("I am post filter from method 2");
			}));
		};
	}
	
	@Order(3)
	@Bean
	public GlobalFilter preAndPostFilter3() {
		return (exchange, chain) -> {
			log.info("I am in pre filter log of method 3");
			return chain.filter(exchange).then(Mono.fromRunnable(() -> {
				log.info("I am post filter from method 3");
			}));
		};
	}
}
