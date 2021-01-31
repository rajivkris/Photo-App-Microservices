package com.rajiv.microservices.users.usersservice.feign.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.rajiv.microservices.users.usersservice.response.model.AlbumsResponseModel;

@FeignClient(name = "albums-ws", fallback = AlbumsFeignClientFallback.class)
public interface AlbumsFeignClient {

	@GetMapping("/users/{userId}/albums")
	public List<AlbumsResponseModel> getAlbums(@PathVariable String userId);
}
