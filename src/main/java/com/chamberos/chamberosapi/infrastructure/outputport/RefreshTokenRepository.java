package com.chamberos.chamberosapi.infrastructure.outputport;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.chamberos.chamberosapi.domain.model.RefreshToken;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    public RefreshToken findByToken(String token);

    public RefreshToken deleteByUserId(String userId);

    public RefreshToken deleteByToken(String token);
}
