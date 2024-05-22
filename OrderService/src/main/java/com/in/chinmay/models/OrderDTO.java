package com.in.chinmay.models;

import com.in.chinmay.enums.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

	String orderById;
	PaymentMode paymentMode;
	Double totalAmount;
	String productId;
	int quantity;
	
}
