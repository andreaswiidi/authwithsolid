package com.solid.auth.repository;

import com.solid.auth.models.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByUserId(Long userId);
}
