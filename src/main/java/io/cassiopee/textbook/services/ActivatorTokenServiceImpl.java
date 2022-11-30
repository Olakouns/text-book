package io.cassiopee.textbook.services;

import io.cassiopee.textbook.entities.ActivatorToken;
import io.cassiopee.textbook.entities.enums.TypeToken;
import io.cassiopee.textbook.exceptions.ResourceNotFoundException;
import io.cassiopee.textbook.repositories.ActivatorTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Random;
import java.util.UUID;

@Service
public class ActivatorTokenServiceImpl implements ActivatorTokenService {

    @Autowired
    private ActivatorTokenRepository tokenRepository;
    private Random random = new Random(1);
    @Value("${token.expiration-time}")
    private long tokenExpirationTime;


    @Override
    public ActivatorToken saveActivatorToken(ActivatorToken activatorToken) {
        return tokenRepository.save(activatorToken);
    }

    @Override
    public ActivatorToken generateUUIDToken(String ref, TypeToken type) {
        String token = UUID.randomUUID().toString();
        while (tokenRepository.existsByToken(token)) {
            token = UUID.randomUUID().toString();
        }

        Instant expiration = Instant.now();
        expiration = expiration.plusMillis(tokenExpirationTime);

        ActivatorToken activatorToken = new ActivatorToken();
        activatorToken.setRef(ref);
        activatorToken.setType(type);
        activatorToken.setToken(token);
        activatorToken.setExpiration(expiration);
        return tokenRepository.save(activatorToken);
    }

    @Override
    public ActivatorToken checkToken(String token, String ref, TypeToken type) {
        return tokenRepository.findByTokenAndRefAndType(token + "", ref, type)
                .orElseThrow(() -> new ResourceNotFoundException("ActivatorToken", "token", token));
    }

    @Override
    public ActivatorToken checkToken(String token, TypeToken type) {
        return tokenRepository.findByTokenAndType(token + "", type)
                .orElseThrow(() -> new ResourceNotFoundException("ActivatorToken", "token", token));
    }

    @Override
    public void deleteToken(UUID id) {
        tokenRepository.deleteById(id);
    }

    @Override
    public void cleanTokenExpired() {
        tokenRepository.deleteByExpirationBefore(Instant.now());
    }
}
