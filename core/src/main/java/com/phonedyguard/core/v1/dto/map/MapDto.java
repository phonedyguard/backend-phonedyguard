package com.phonedyguard.core.v1.dto.map;

import com.phonedyguard.core.entity.MapEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MapDto {
    private long id;
    private double latitude;
    private double longitude;

    @Builder
    public MapDto(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
