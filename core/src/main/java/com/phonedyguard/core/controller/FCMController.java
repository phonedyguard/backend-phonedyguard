package com.phonedyguard.core.controller;

import com.phonedyguard.core.v1.dto.request.FcmRequestDto;
import com.phonedyguard.core.v1.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/users/messages")
@RequiredArgsConstructor
public class FCMController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("")
    public ResponseEntity<FcmRequestDto> pushMessage(@RequestBody FcmRequestDto fcmRequestDto) throws IOException {
        System.out.println(fcmRequestDto.getTargetToken() + " "
                +fcmRequestDto.getTitle() + " " + fcmRequestDto.getBody());

        firebaseCloudMessageService.sendMessageTo(
                fcmRequestDto.getTargetToken(),
                fcmRequestDto.getTitle(),
                fcmRequestDto.getBody());
        return ResponseEntity.ok().build();
    }
}
