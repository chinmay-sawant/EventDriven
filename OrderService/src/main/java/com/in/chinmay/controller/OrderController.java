package com.in.chinmay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.chinmay.enums.PaymentMode;
import com.in.chinmay.models.OrderDTO;
import com.in.chinmay.models.OrderModel;
import com.in.chinmay.services.OrderService;

@RestController
@RequestMapping("/v1/order")
@CrossOrigin(origins = {"http://localhost:7000"})
public class OrderController {

	@Autowired
	OrderService orderService;

	@GetMapping
	public Page<OrderModel> allOrders(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return orderService.getAllOrders(page, size);
	}
	
	@PostMapping
	public OrderModel createNewOrder(@RequestBody OrderDTO orderDTO) {
		return orderService.createOrder(orderDTO.getOrderById(), orderDTO.getPaymentMode(), 
				orderDTO.getTotalAmount(), orderDTO.getProductId(), orderDTO.getQuantity());
	}
}
