package com.rajiv.microservices.users.usersservice.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.rajiv.microservices.users.usersservice.dto.UserDTO;
import com.rajiv.microservices.users.usersservice.entity.UserEntity;
import com.rajiv.microservices.users.usersservice.feign.client.AlbumsFeignClient;
import com.rajiv.microservices.users.usersservice.repository.UserRepository;
import com.rajiv.microservices.users.usersservice.response.model.AlbumsResponseModel;

@Service
public class UserManagementService implements UserDetailsService {

	private UserRepository userRepo;
	
	private BCryptPasswordEncoder bcryptPassEncoder;
	
	private ModelMapper mapper;
	
	@Autowired
	@LoadBalanced
	private RestTemplate restTemplate;
	
	@Autowired
	private AlbumsFeignClient feignClient;
	
	private Environment env;
	
	@Autowired
	public UserManagementService(UserRepository userRepo, BCryptPasswordEncoder bcryptPassEncoder, ModelMapper mapper, Environment env) {
		this.bcryptPassEncoder = bcryptPassEncoder;
		this.userRepo = userRepo;
		this.mapper = mapper;
		this.env = env;
	}
	
	public UserDTO createUser(UserDTO dto) {
		dto.setUserId(UUID.randomUUID().toString());
		dto.setEncrptPassword(bcryptPassEncoder.encode(dto.getPassword()));
		UserEntity userEntity = mapper.map(dto, UserEntity.class);
		userRepo.save(userEntity);
		UserDTO resDto = mapper.map(userEntity, UserDTO.class);
		return resDto;
	}
	
	public UserDTO fetchByUsername(String name) {
		final UserEntity user = userRepo.findByEmail(name);
		if (user == null) {
			throw new UsernameNotFoundException("The user " + name + " doesnot exist");
		}
		
		return mapper.map(user, UserDTO.class);
	}
	
	public UserDTO fetchByUserId(String userId) {
		final UserEntity entity = userRepo.findByUserId(userId);
		if (entity == null) {
			throw new UsernameNotFoundException("User with " + userId + " not found");
		}
		final UserDTO dto = mapper.map(entity, UserDTO.class);
		final String albumsUrl = String.format(env.getProperty("albums.url"), userId);
		//final ResponseEntity<List<AlbumsResponseModel>> response = restTemplate.exchange(albumsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<AlbumsResponseModel>>() {
		//});
		
		final List<AlbumsResponseModel> albumsResponse = feignClient.getAlbums(userId);
		dto.setAlbumsRes(albumsResponse);
		return dto;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity user = userRepo.findByEmail(username);
		return new User(username, user.getEncrptPassword(), new ArrayList<>());
	}
}
