package io.cassiopee.textbook.services;



import io.cassiopee.textbook.entities.ActivatorToken;
import io.cassiopee.textbook.entities.enums.TypeToken;

import java.util.UUID;

public interface ActivatorTokenService {

    ActivatorToken saveActivatorToken(ActivatorToken activatorToken);

    ActivatorToken generateUUIDToken(String ref, TypeToken type );

    ActivatorToken checkToken(String token, String ref, TypeToken type);

    ActivatorToken checkToken(String token, TypeToken type);

    void deleteToken(UUID id);

    void cleanTokenExpired();
}
