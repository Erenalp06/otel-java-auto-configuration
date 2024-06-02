package com.teksen.opentelemetrydemo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("api/v1/external")
public class ExternalAPIController {

    private final RestTemplate restTemplate;    

    public ExternalAPIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public  ResponseEntity<String> getMethodName() {
        String URL = "https://api.thecatapi.com/v1/images/search";
        ResponseEntity<String> response = restTemplate.getForEntity(URL, String.class);
        return response;
    }
    
}
