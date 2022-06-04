package com.phonedyguard.core.v1.dto.map;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MapSafeLatitude {
    private double safe_latitude;
    @Builder
    public MapSafeLatitude(double safe_latitude) {
        this.safe_latitude = safe_latitude;
    }
}
