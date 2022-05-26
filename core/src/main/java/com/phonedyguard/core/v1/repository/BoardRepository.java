package com.phonedyguard.core.v1.repository;

import com.phonedyguard.core.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
}
