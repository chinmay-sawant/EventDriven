package com.in.chinmay.models;

import org.springframework.data.mongodb.core.mapping.Document;

import com.in.chinmay.enums.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "orders")
public class OrderModel {

	String uniqueOrderId;
	String orderedBy;
	String productId;
	String timestamp;
	PaymentMode paymentMode;
	Double totalAmount;
	int quanitity;
	
}
