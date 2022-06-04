package com.phonedyguard.core.controller;

import com.phonedyguard.core.v1.dto.request.TestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public ResponseEntity<TestDto> test3() {
        TestDto testDto = new TestDto("hi", "hi2");
        return new ResponseEntity<TestDto>(testDto, HttpStatus.OK);
    }

    @GetMapping("/test4")
    public ResponseEntity<List> test4() {
        List<TestDto> testDtoList = new ArrayList<>();
        testDtoList.add(new TestDto("aa", "aaa"));
        testDtoList.add(new TestDto("bb", "bbb"));
        testDtoList.add(new TestDto("cc", "ccc"));
        return new ResponseEntity<List>(testDtoList, HttpStatus.OK);
    }

    //test
    //test2

}
































































