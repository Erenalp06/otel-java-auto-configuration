package com.teksen.opentelemetrydemo.service;

import org.springframework.stereotype.Service;

import com.teksen.opentelemetrydemo.entity.User;
import com.teksen.opentelemetrydemo.repository.UserRepository;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User addUser(User user){
        return userRepository.save(user);
    }

}