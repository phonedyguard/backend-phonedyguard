package com.phonedyguard.core.v1.dto.map;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class MapDto {
    private long id;
    private String email;
    private double latitude;
    private double longitude;

    @Builder
    public MapDto(long id,String email, double latitude, double longitude) {
        this.id = id;
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
