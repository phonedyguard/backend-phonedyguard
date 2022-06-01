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

    public MapEntity toEntity(){
        MapEntity map_Entity = MapEntity.builder()
                .id(id)
                .latitude(latitude)
                .longitude(longitude)
                .build();
        return map_Entity;
    }

    @Builder
    public MapDto(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
