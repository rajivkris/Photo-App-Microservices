package com.rajiv.microservices.users.usersservice.feign.client;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.rajiv.microservices.users.usersservice.response.model.AlbumsResponseModel;

import feign.FeignException;

@Component
public class AlbumsFeignClientFallback implements AlbumsFeignClient {

	
	@Override
	public List<AlbumsResponseModel> getAlbums(String userId) {
		
		return new ArrayList<>();
	}

}
