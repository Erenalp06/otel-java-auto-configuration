package com.teksen.opentelemetrydemo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.teksen.opentelemetrydemo.entity.User;
import com.teksen.opentelemetrydemo.service.UserService;

import io.opentelemetry.api.trace.Span;

import org.springframework.web.bind.annotation.GetMapping;
import java.util.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("api/v1/users")
public class UserController {

    private final UserService userService; 
    private final ObjectMapper objectMapper;   

    public UserController(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @PostMapping
    public User addNewUser(@RequestBody User user) {
        Span currentSpan = Span.current();
        if (currentSpan != null) {
            try {
                String userJson = objectMapper.writeValueAsString(user);
                currentSpan.setAttribute("http.request.body", userJson);
            } catch (JsonProcessingException e) {
                currentSpan.setAttribute("http.request.body.error", "Failed to convert user to JSON");
            }
        }
        return userService.addUser(user);
    }
}
