package com.phonedygaurd.core.controller;



import com.phonedygaurd.core.jwt.JwtTokenProvider;
import com.phonedygaurd.core.lib.Helper;
import com.phonedygaurd.core.v1.dto.Response;
import com.phonedygaurd.core.v1.dto.request.UserRequestDto;
import com.phonedygaurd.core.v1.repository.UserRepository;
import com.phonedygaurd.core.v1.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final Response response;

    @Autowired
    private final UserRepository userRepository;

    @GetMapping("")
    public ResponseEntity<?> getUser(HttpServletRequest request){
        return userService.getUser(request);
    }

    @PostMapping("")
    public ResponseEntity<?> signUp(@RequestBody UserRequestDto.SignUp signUp, Errors errors) {
        // validation check
        log.info("sign up: " + signUp);
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.signUp(signUp);
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(HttpServletRequest request, @RequestBody UserRequestDto.updateUser updateUser){
        return userService.updateUser(request, updateUser);
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(HttpServletRequest request){
        return userService.deleteUser(request);
    }

    @PostMapping("/events")
    public ResponseEntity<?> login(@RequestBody UserRequestDto.Login login, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.login(login);
    }

    @PostMapping("/tokens")
    public ResponseEntity<?> reissue(@RequestBody UserRequestDto.Reissue reissue, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            log.info("reissue error");
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.reissue(reissue);
    }

    @DeleteMapping("/events")
    public ResponseEntity<?> logout(@RequestBody UserRequestDto.Logout logout, Errors errors) {
        // validation check
        if (errors.hasErrors()) {
            return response.invalidFields(Helper.refineErrors(errors));
        }
        return userService.logout(logout);
    }

    @GetMapping("/authority")
    public ResponseEntity<?> authority() {
        log.info("ADD ROLE_ADMIN");
        return userService.authority();
    }

    @GetMapping("/userTest")
    public ResponseEntity<?> userTest(HttpServletRequest request) {

        log.info("ROLE_USER TEST");
        return response.success();
    }

    @GetMapping("/adminTest")
    public ResponseEntity<?> adminTest() {
        log.info("ROLE_ADMIN TEST");
        return response.success();
    }
}
