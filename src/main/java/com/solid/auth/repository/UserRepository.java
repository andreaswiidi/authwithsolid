package com.solid.auth.repository;

import com.solid.auth.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * S -> to manage data access for the Users entity
 * O -> can add more query methods without modifying existing code.
 * D -> allows high-level modules to use this interface
 */

public interface UserRepository extends JpaRepository<Users,Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByEmail(String email);
}
