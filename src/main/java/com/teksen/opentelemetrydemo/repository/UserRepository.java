package com.teksen.opentelemetrydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.teksen.opentelemetrydemo.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long>{    
    
}