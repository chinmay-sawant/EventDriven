package com.in.chinmay.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.chinmay.models.Product;
import com.in.chinmay.services.ProductService;

@RestController
@RequestMapping("/v1/product")
public class ProductController{

	@Autowired
	ProductService productService;
	
	@GetMapping("/generate-users")
    public String generateUsers(@RequestParam int count) {
        productService.generateRandomProducts(count);
        return count + " users generated.";
    }
	 
	@GetMapping
	public Page<Product> getProducts(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		return productService.getProducts(page, size);
	}
	
}
