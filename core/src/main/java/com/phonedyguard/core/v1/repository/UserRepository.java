package com.phonedyguard.core.v1.repository;

import com.phonedyguard.core.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByEmail(String email);
    Optional<List<Users>> findAllByEmail(String email);
    boolean existsByEmail(String email);
}
