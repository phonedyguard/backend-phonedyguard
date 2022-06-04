package com.phonedyguard.core.v1.repository;

import com.phonedyguard.core.entity.MapEntity;
import com.phonedyguard.core.entity.MapSafeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MapSafeRepository extends JpaRepository<MapSafeEntity, Long> {
    Optional<List<MapSafeEntity>> findAllByEmail(String email);
}
