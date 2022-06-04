package com.phonedyguard.core.v1.dto.map;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class Routes {
    private double safe_latitude;
    private double safe_longitude;

    @Builder
    public Routes(double safe_latitude, double safe_longitude) {
        this.safe_latitude = safe_latitude;
        this.safe_longitude = safe_longitude;
    }
}
