package com.rajiv.microservices.users.usersservice.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rajiv.microservices.users.usersservice.dto.UserDTO;
import com.rajiv.microservices.users.usersservice.request.model.UserRequestModel;
import com.rajiv.microservices.users.usersservice.response.model.UserResponseModel;
import com.rajiv.microservices.users.usersservice.service.UserManagementService;

@RestController
@RequestMapping("/users")
public class UserServiceController {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private UserManagementService userService;
	
	@GetMapping("/status")
	public String status() {
		return "The secret key used is "  + env.getProperty("secret.key"); 
	}
	
	@PostMapping("/user/create")
	public  ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel request) {
		final ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDTO reqDto = mapper.map(request, UserDTO.class);
		UserDTO resDto = userService.createUser(reqDto);
		final UserResponseModel resModel =  mapper.map(resDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(resModel);
	}

}
