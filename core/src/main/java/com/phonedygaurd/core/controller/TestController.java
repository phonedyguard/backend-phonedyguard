package com.phonedygaurd.core.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test1")
    public ResponseEntity test1(){
        return new ResponseEntity<String>("test1 Success", HttpStatus.OK);
    }
}
