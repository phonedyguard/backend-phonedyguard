package com.phonedyguard.core.v1.dto.map;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MapSafeLongitude {
    private double safe_longitude;

    @Builder
    public MapSafeLongitude(double safe_longitude) {
        this.safe_longitude = safe_longitude;
    }
}
