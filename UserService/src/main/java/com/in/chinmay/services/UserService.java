package com.in.chinmay.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.github.javafaker.Faker;
import com.in.chinmay.models.OrderEvent;
import com.in.chinmay.models.User;
import com.in.chinmay.models.dto.UserInfoDTO;
import com.in.chinmay.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final Faker faker = new Faker();

    public User createRandomUser() {
        String username = faker.name().username();
        String userpass = faker.internet().password();
        Double balance = faker.number().randomDouble(2, 0, 10000);

        User user = new User(username, userpass, balance);
        return userRepository.save(user);
    }

    public void generateRandomUsers(int count) {
        for (int i = 0; i < count; i++) {
            createRandomUser();
            
        }
    }
    
    public boolean userExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }
    
    
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    
    public UserInfoDTO convertToDto(User user) {
        return new UserInfoDTO(user.getId(),user.getUsername(), user.getBalance());
    }
    
    public void updateBalance(String id,Double newBalance) {
    	 Optional<User> userOptional = userRepository.findById(id);
         if (userOptional.isPresent()) {
             User user = userOptional.get();
             user.setBalance(newBalance);
             userRepository.save(user);
         
         } else {
             throw new RuntimeException("User not found");
         }
        
    }
    
    public void addBalance(String id,Double newBalance) {
   	 Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setBalance(user.getBalance()+newBalance);
            userRepository.save(user);

        } else {
            throw new RuntimeException("User not found");
        }
       
   }
    
    public void deductBalance(String id,Double newBalance) {
      	 Optional<User> userOptional = userRepository.findById(id);
           if (userOptional.isPresent()) {
               User user = userOptional.get();
               if(user.getBalance()>0)
            	   user.setBalance(user.getBalance()-newBalance);
               else
            	   throw new RuntimeException("Low On Balance !");
               userRepository.save(user);

           } else {
               throw new RuntimeException("User not found");
           }
          
      }
    
    @KafkaListener(topics = "order-events", groupId = "user-service-group")
    public void handleOrderEvent(OrderEvent event) {
        // Process the order event
        // Update user data based on the event (e.g., update order status)
        System.out.println("UserService - orderEvent - "+ event);
        // You can perform other actions based on the event
    }
   

}
