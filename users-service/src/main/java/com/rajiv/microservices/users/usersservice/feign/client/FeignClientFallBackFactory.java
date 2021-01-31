package com.rajiv.microservices.users.usersservice.feign.client;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallBackFactory implements FallbackFactory<AlbumsFeignClient>{

	@Override
	public AlbumsFeignClient create(Throwable cause) {
		// TODO Auto-generated method stub
		return new AlbumsFeignClientFallback();
	}

}
