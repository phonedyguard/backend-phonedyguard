package com.phonedyguard.core.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Data
@NoArgsConstructor
@Table(name = "mapsafe")
public class MapSafeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(length = 255, nullable = false)
    private double safe_latitude;

    @Column(length = 255, nullable = false)
    private double safe_longitude;

    @Builder
    public MapSafeEntity(long id, double safe_latitude, double safe_longitude) {
        this.id = id;
        this.safe_latitude = safe_latitude;
        this.safe_longitude = safe_longitude;
    }
}
