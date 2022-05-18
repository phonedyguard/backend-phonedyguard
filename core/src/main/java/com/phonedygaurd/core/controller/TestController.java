package com.phonedygaurd.core.controller;

import com.phonedygaurd.core.v1.dto.request.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class TestController {
    @GetMapping("/test1")
    public ResponseEntity test1(){
        return new ResponseEntity<String>("test1 Success", HttpStatus.OK);
    }

    @PostMapping("/test2")
    public ResponseEntity<TestDto> test2(@RequestBody TestDto testDto){
        log.info(testDto.getTestStr() + testDto.getTestStr2());
        return new ResponseEntity<TestDto>(testDto, HttpStatus.OK);
    }

    @GetMapping("/test3")
    public ResponseEntity<String> test3(@RequestParam String testStr1, @RequestParam String testStr2) {
        return new ResponseEntity<String>(testStr1 + testStr2, HttpStatus.OK);
    }
    // test 주석
}
































































