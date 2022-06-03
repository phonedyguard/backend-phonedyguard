package com.phonedyguard.core.v1.repository;

import com.phonedyguard.core.entity.MapSafeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface MapSafeRepository extends JpaRepository<MapSafeEntity, Long> {
    List<MapSafeEntity> findByEmail(String email);
//    Optional<MapSafeEntity> findByEmail(String email);
}
