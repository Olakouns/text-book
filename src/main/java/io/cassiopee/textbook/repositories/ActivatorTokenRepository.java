package io.cassiopee.textbook.repositories;

import io.cassiopee.textbook.entities.ActivatorToken;
import io.cassiopee.textbook.entities.enums.TypeToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActivatorTokenRepository extends JpaRepository<ActivatorToken, UUID> {
    Optional<ActivatorToken> findByToken(String token);
    List<ActivatorToken> findByRefOrderByCreatedAtDesc(String ref);
    Optional<ActivatorToken> findByTokenAndRefAndType(String token, String ref, TypeToken type);
    Optional<ActivatorToken> findByRefAndType(String ref, TypeToken type);
    Optional<ActivatorToken> findByTokenAndType(String token, TypeToken type);
    void deleteByExpirationBefore(Instant now);
    boolean existsByToken(String token);
}
