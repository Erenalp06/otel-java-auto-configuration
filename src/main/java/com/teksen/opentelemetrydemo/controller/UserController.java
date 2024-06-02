package com.teksen.opentelemetrydemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.teksen.opentelemetrydemo.entity.User;
import com.teksen.opentelemetrydemo.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService;    

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public User addNewUser(@RequestBody User user) {      
        return userService.addUser(user);
    }      
}
