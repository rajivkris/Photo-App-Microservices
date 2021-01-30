package com.rajiv.microservices.users.usersservice.controller;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/status")
	public String status() {
		return "The secret key used is "  + env.getProperty("secret.key"); 
	}
	
	@PostMapping(value =  "/user/create", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
	public  ResponseEntity<UserResponseModel> createUser(@Valid @RequestBody UserRequestModel request) {
		UserDTO reqDto = mapper.map(request, UserDTO.class);
		UserDTO resDto = userService.createUser(reqDto);
		final UserResponseModel resModel =  mapper.map(resDto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(resModel);
	}
	
	@GetMapping(value = "/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<UserResponseModel> getUser(@PathVariable String userId) {
		final UserDTO dto = userService.fetchByUserId(userId);
		final UserResponseModel model = mapper.map(dto, UserResponseModel.class);
		return ResponseEntity.status(HttpStatus.OK).body(model);
	}
	

}
