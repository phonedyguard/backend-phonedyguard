package com.phonedyguard.core.v1.repository;

import com.phonedyguard.core.entity.BoardEntity;
import com.phonedyguard.core.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    Optional<BoardEntity> findByEmail(String email);
}
