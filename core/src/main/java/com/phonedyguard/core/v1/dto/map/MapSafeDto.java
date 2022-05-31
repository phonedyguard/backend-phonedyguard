package com.phonedyguard.core.v1.dto.map;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MapSafeDto {
    private long id;
    private double safe_latitude;
    private double safe_longitude;

    public MapSafeEntity toEntity(){
        MapSafeEntity mapSafeEntity = MapSafeEntity.builder()
                .id(id)
                .safe_latitude(safe_latitude)
                .safe_longitude(safe_longitude)
                .build();
        return mapSafeEntity;
    }

    @Builder
    public MapSafeDto(long id, double safe_latitude, double safe_longitude) {
        this.id = id;
        this.safe_latitude = safe_latitude;
        this.safe_longitude = safe_longitude;
    }
}
