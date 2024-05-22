package com.in.chinmay.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.in.chinmay.models.OrderModel;

public interface OrderRepository extends MongoRepository<OrderModel, String>{

	
	
}
