package com.solid.auth.repository;

import com.solid.auth.models.Session;
import com.solid.auth.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session,Long> {
    Optional<Session> findByUserId(Long userId);
}
