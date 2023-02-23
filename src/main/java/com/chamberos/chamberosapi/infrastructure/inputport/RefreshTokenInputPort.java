package com.chamberos.chamberosapi.infrastructure.inputport;

import com.chamberos.chamberosapi.domain.model.RefreshToken;

public interface RefreshTokenInputPort {
    public RefreshToken save(RefreshToken refreshToken);

    public RefreshToken findByToken(String refreshToken);

    public RefreshToken deleteByUserId(String userId);

    public void deleteByToken(String refreshToken);
}