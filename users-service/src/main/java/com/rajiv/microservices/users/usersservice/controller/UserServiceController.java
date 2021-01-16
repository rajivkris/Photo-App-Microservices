package com.rajiv.microservices.users.usersservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserServiceController {
	
	@GetMapping("/status")
	public String status() {
		return "I am working";
	}

}
