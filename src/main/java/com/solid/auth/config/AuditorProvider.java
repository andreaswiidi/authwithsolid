package com.solid.auth.config;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditorProvider implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        // Return the username of the authenticated user
        // Replace with logic to fetch the current user's username, e.g., from Spring Security's context
        return Optional.of("testing"); // Use a default value if no authentication context is available
    }
}