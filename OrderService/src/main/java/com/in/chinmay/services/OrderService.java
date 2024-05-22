package com.in.chinmay.services;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import com.in.chinmay.enums.PaymentMode;
import com.in.chinmay.models.OrderEvent;
import com.in.chinmay.models.OrderModel;
import com.in.chinmay.repository.OrderRepository;

@Service
public class OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	private KafkaTemplate<String, OrderEvent> kafkaTemplate;

	public Page<OrderModel> getAllOrders(int page, int size) {
        // Create a PageRequest for pagination
        PageRequest pageRequest = PageRequest.of(page, size);
        // Fetch products using pagination
        return orderRepository.findAll(pageRequest);
    }
	
	 public OrderModel createOrder(String userId, PaymentMode paymentMode, Double totalOrderAmount,String productId, int quanitity) {
		 OrderModel order = new OrderModel();
		 String uniqueOrderId = generateUniqueOrderId();
		 String purchasedOn = LocalDateTime.now().toString();
		 
	        order.setUniqueOrderId(uniqueOrderId);
	        order.setProductId(productId);
	        order.setOrderedBy(userId);
	        order.setTimestamp(purchasedOn);
	        order.setPaymentMode(paymentMode);
	        order.setTotalAmount(totalOrderAmount);
	        order.setQuanitity(quanitity);
	        OrderEvent orderEvent = new OrderEvent(order.getUniqueOrderId(), order.getProductId(), 
	        		order.getOrderedBy(), order.getTimestamp(), order.getQuanitity());

	        String topic = "order-events"; // Replace with your topic name
	        try {
	            SendResult<String, OrderEvent> result = kafkaTemplate.send(topic, orderEvent).get();
	            System.out.println("Order event sent successfully: " + result.getRecordMetadata().offset());
	        } catch (RuntimeException | InterruptedException | ExecutionException e) {
	            System.err.println("Failed to send order event: " + e.getMessage());
	        }
	        return orderRepository.save(order);
	    }
	 
	 private String generateUniqueOrderId() {
	        return java.util.UUID.randomUUID().toString();
	   }
	 

	
	
}
