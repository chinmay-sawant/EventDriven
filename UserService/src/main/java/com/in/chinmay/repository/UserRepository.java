package com.in.chinmay.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.in.chinmay.models.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	 Optional<User> findByUsername(String username);
	
}