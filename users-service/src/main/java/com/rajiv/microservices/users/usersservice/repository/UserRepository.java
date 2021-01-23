package com.rajiv.microservices.users.usersservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.rajiv.microservices.users.usersservice.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
	
	UserEntity findByEmail(String email);

}
