package com.rajiv.microservices.accounts.accountsservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

	@GetMapping("/status")
	public String status() {
		return "I am working from accounts";
	}
}
