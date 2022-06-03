package com.phonedyguard.core.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "mapsafe")
public class MapSafeEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;

    private String email;

    @Column(length = 255, nullable = false)
    private double safe_latitude;

    @Column(length = 255, nullable = false)
    private double safe_longitude;
}
