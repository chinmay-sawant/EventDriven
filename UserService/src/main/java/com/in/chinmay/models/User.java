package com.in.chinmay.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	 @Id
	 private String id;
	 private String username;
	 private String userpass;
	 private Double balance;

	 
	  public User(String username, String userpass, Double balance) {
	        this.username = username;
	        this.userpass = userpass;
	        this.balance = balance;
	    }

}
