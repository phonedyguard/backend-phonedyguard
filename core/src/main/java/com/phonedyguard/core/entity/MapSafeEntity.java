package com.phonedyguard.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Data
@NoArgsConstructor
@Table(name = "mapsafe")
public class MapSafeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private double safe_latitude;

    @Column(length = 255, nullable = false)
    private double safe_longitude;

    @Builder
    public MapSafeEntity(long id, String email, double safe_latitude, double safe_longitude) {
        this.id = id;
        this.email = email;
        this.safe_latitude = safe_latitude;
        this.safe_longitude = safe_longitude;
    }
}
