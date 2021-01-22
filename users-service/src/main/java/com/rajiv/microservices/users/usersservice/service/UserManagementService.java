package com.rajiv.microservices.users.usersservice.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.rajiv.microservices.users.usersservice.dto.UserDTO;
import com.rajiv.microservices.users.usersservice.entity.UserEntity;
import com.rajiv.microservices.users.usersservice.repository.UserRepository;

@Service
public class UserManagementService {

	private UserRepository userRepo;
	
	private BCryptPasswordEncoder bcryptPassEncoder;
	
	@Autowired
	public UserManagementService(UserRepository userRepo, BCryptPasswordEncoder bcryptPassEncoder) {
		this.bcryptPassEncoder = bcryptPassEncoder;
		this.userRepo = userRepo;
	}
	
	public UserDTO createUser(UserDTO dto) {
		dto.setUserId(UUID.randomUUID().toString());
		dto.setEncrptPassword(bcryptPassEncoder.encode(dto.getPassword()));
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserEntity userEntity = mapper.map(dto, UserEntity.class);
		userRepo.save(userEntity);
		UserDTO resDto = mapper.map(userEntity, UserDTO.class);
		return resDto;
	}
}
