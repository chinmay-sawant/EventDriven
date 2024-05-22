package com.in.chinmay.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.in.chinmay.models.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
	
	
	
}
