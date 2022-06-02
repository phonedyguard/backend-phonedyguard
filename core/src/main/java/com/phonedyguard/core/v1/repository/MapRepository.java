package com.phonedyguard.core.v1.repository;

import com.phonedyguard.core.entity.BoardEntity;
import com.phonedyguard.core.entity.MapEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MapRepository extends JpaRepository<MapEntity, Long> {
    Optional<MapEntity> findByEmail(String email);
}
