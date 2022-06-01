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

    private String email;

    @Column(length = 100, nullable = false)
    private String email;

    @Column(length = 100, nullable = false)
    private double latitude;

    @Column(length = 100, nullable = false)
    private double longitude;

    @Builder
<<<<<<< HEAD
    public MapEntity(String email, double latitude, double longitude) {
=======
    public MapEntity(long id, String email, double latitude, double longitude) {
        this.id = id;
>>>>>>> develop
        this.email = email;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
