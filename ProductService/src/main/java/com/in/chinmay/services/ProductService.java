package com.in.chinmay.services;

import java.util.Locale;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.in.chinmay.models.OrderEvent;
import com.in.chinmay.models.Product;
import com.in.chinmay.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	private final Faker faker = new Faker(Locale.ENGLISH);

	public void generateRandomProducts(int count) {
		for (int i = 0; i < count; i++) {
			Product product = new Product();
			product.setName(faker.commerce().productName());
			product.setPrice(faker.number().randomDouble(2, 1, 1000));
			product.setDescription(faker.lorem().sentence());
			product.setQuantity(faker.number().numberBetween(1, 100)); // Generate random quantity
			productRepository.save(product);
		}
	}

	public Page<Product> getProducts(int page, int size) {
        // Create a PageRequest for pagination
        PageRequest pageRequest = PageRequest.of(page, size);
        // Fetch products using pagination
        return productRepository.findAll(pageRequest);
    }
	
	 @KafkaListener(topics = "order-events", groupId = "product-service-group")
	    public void handleOrderEvent(OrderEvent event) {
	        // Process the order event
	        // Update user data based on the event (e.g., update order status)
	        System.out.println("ProductService - orderEvent - "+ event);
	        Optional<Product> productOptional = productRepository.findById(event.getProductId());
	        if (productOptional.isPresent()) {
	            Product product = productOptional.get();
	            int originalQuantity = product.getQuantity();
	            int updatedQuantity = originalQuantity - event.getQuantity();
	            
	            // Update the product's quantity
	            product.setQuantity(updatedQuantity);
	            
	            // Save the updated product back to the database
	            productRepository.save(product);
	            
	            // Print the quantities for verification
	            System.out.println("Original quantity: " + originalQuantity);
	            System.out.println("Updated quantity: " + updatedQuantity);
	        } else {
	            // Handle the case where the product is not found
	            System.out.println("Product not found for ID: " + event.getProductId());
	        }
	    }
}
