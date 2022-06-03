package com.phonedyguard.core.v1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MapRequestDto {
    private double startX;
    private double startY;
    private double endX;
    private double endY;
}
