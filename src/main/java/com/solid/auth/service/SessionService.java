package com.solid.auth.service;


import com.solid.auth.models.Session;
import com.solid.auth.repository.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SessionService {
    private final SessionRepository sessionRepository;

    @Value("${jwt.session-expiration-time}")
    private int expiration;

    @Transactional
    public Session createSessionForUser(Long userId){
        Date expDate = Date.from(Instant.now().plus(expiration, ChronoUnit.MINUTES));
        Optional<Session> session = sessionRepository.findByUserId(userId);
        if (session.isEmpty()){
            Session newSession = new Session();
            newSession.setUserId(userId);
            newSession.setExpSession(expDate);
            return sessionRepository.save(newSession);
        }
        session.get().setExpSession(expDate);
        return sessionRepository.save(session.get());
    }

    public Optional<Session> findSessionById(Long id){
        return sessionRepository.findById(id);
    }

    public boolean isSessionExpired(Long userId){
        Optional<Session> session = sessionRepository.findByUserId(userId);
        return session.map(value -> value.getExpSession().before(new Date())).orElse(false);
    }
}
