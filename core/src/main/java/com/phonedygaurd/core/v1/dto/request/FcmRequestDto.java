package com.phonedygaurd.core.v1.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FcmRequestDto {
    private String targetToken;
    private String title;
    private String body;
}
