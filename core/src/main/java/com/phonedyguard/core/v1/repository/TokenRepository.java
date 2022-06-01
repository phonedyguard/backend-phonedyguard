package com.phonedyguard.core.v1.repository;

import com.phonedyguard.core.entity.Tokens;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Tokens, Long> {
    Optional<Tokens> findByToken(String token);
}
