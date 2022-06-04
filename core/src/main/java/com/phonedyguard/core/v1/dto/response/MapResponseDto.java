package com.phonedyguard.core.v1.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MapResponseDto {
    private double latitude;
    private double longitude;
}
