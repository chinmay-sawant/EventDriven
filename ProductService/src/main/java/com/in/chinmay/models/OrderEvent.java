package com.in.chinmay.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderEvent {
	
	private String orderId;
	private String productId;
	private String purchasedBy;
	private String purchaseOn;
	private int quantity;
	
}
