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
@Table(name = "map")
public class MapEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private double latitude;

    @Column(length = 100, nullable = false)
    private double longitude;

    @Builder
    public MapEntity(long id, double latitude, double longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
