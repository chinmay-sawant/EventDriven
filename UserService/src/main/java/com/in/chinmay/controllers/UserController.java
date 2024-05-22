package com.in.chinmay.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.in.chinmay.models.User;
import com.in.chinmay.models.dto.UserInfoDTO;
import com.in.chinmay.services.UserService;

@RestController
@RequestMapping("/v1/user")
public class UserController {


    @Autowired
    private UserService userService;

    @GetMapping("/generate-users")
    public String generateUsers(@RequestParam int count) {
        userService.generateRandomUsers(count);
        return count + " users generated.";
    }
	
	@GetMapping("/check-user/{username}")
    public String checkUserExists(@PathVariable String username) {
        boolean exists = userService.userExists(username);
        return exists ? "User exists" : "User does not exist";
    }

    @GetMapping("/get-user-info/{username}")
    public Optional<UserInfoDTO> getUser(@PathVariable String username) {
    	 Optional<User> userOptional = userService.getUserByUsername(username);
    	 return userOptional.map(userService::convertToDto);
    }
    
    @PutMapping("/update-balance/{id}")
    public String updateBalance(@PathVariable String id, @RequestParam Double balance) {
        try {
            userService.updateBalance(id, balance);
            return "Balance updated successfully";
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
    @PutMapping("/add-balance/{id}")
    public String addBalance(@PathVariable String id, @RequestParam Double balance) {
        try {
            userService.addBalance(id, balance);
            return String.format("%s Amount added successfully for user %s", balance, id);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
    @PutMapping("/deduct-balance/{id}")
    public String deductBalance(@PathVariable String id, @RequestParam Double balance) {
        try {
            userService.deductBalance(id, balance);
            return String.format("Deducted %s for userId - ",balance,id);
        } catch (RuntimeException e) {
            return e.getMessage();
        }
    }
    
}
