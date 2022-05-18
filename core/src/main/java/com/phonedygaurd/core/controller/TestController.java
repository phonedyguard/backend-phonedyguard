package com.phonedygaurd.core.controller;

import com.phonedygaurd.core.v1.dto.request.TestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/test1")
    public ResponseEntity test1(){
        return new ResponseEntity<String>("test1 Success", HttpStatus.OK);
    }

    @GetMapping("/test2")
    public ResponseEntity<TestDto> test2(@RequestBody TestDto testDto){
        return new ResponseEntity<TestDto>(testDto, HttpStatus.OK);
    }

    @GetMapping("/test3")
    public ResponseEntity<String> test3(@RequestParam String testStr1, @RequestParam String testStr2) {
        return new ResponseEntity<String>(testStr1 + testStr2, HttpStatus.OK);
    }
    // test 주석
}
































































