package com.teksen.opentelemetrydemo.controller;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@RestController
@RequestMapping("api/v1/external")
public class ExternalAPIController {

    private final RestTemplate restTemplate;    

    public ExternalAPIController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

     public Map<String, String> requestMap(){
        Map<String, String> myMap = new HashMap<>();
        myMap.put("1", "https://api.tvmaze.com/shows/1?embed=images");
        myMap.put("2", "https://api.deezer.com/artist/1");
        myMap.put("3", "https://randomuser.me/api");
        myMap.put("4", "https://ron-swanson-quotes.herokuapp.com/v2/quotes");
        return myMap;
    }        

    @GetMapping("/{param}")
    public ResponseEntity<String> getMethodName(@PathVariable String param) {
        Map<String, String> myMap = requestMap();
        ResponseEntity<String> response = restTemplate.getForEntity(myMap.get(param), String.class);
        return response;
    }  
    
}
