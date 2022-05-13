package com.phonedygaurd.core.controller;

import com.phonedygaurd.core.v1.dto.request.FcmRequestDto;
import com.phonedygaurd.core.v1.service.FirebaseCloudMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FCMController {
    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @PostMapping("/api/fcm")
    public ResponseEntity<FcmRequestDto> pushMessage(@RequestBody FcmRequestDto fcmRequestDto) throws IOException {
        System.out.println(fcmRequestDto.getTargetToken() + " "
                +fcmRequestDto.getTitle() + " " + fcmRequestDto.getBody());

        firebaseCloudMessageService.sendMessageTo(
                fcmRequestDto.getTargetToken(),
                fcmRequestDto.getTitle(),
                fcmRequestDto.getBody());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/fcm")
    public void outRoute() throws IOException {
        firebaseCloudMessageService.sendMessageTo("cNPPxBb_QdG1ZC_VEkNmWB:APA91bFYh3iRBhc7yvYqJVyYsPX9CQGW-XLsAxresm8xJ2fF7m3Bxh432ZdRCb5eLuIdCF8yIFNkMdOtj_KTBlLZtSKbtXsUQusDlyWvZVWB-KCIYfmHrt43nTgJn2QyR44Rq_Pvsxon", "폰디가드", "피보호자가 안심경로를 벗어났습니다.");
    }
}
