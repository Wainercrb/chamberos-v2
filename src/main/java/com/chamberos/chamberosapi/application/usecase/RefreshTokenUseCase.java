package com.chamberos.chamberosapi.application.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.chamberos.chamberosapi.domain.model.RefreshToken;
import com.chamberos.chamberosapi.infrastructure.inputport.RefreshTokenInputPort;
import com.chamberos.chamberosapi.infrastructure.outputport.RefreshTokenRepository;

@Component
public class RefreshTokenUseCase implements RefreshTokenInputPort {

    @Autowired(required = true)
    RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public void deleteByToken(String refreshToken) {
        refreshTokenRepository.deleteByToken(refreshToken);
    }

    @Override
    public RefreshToken findByToken(String refreshToken) {
        return refreshTokenRepository.findByToken(refreshToken);
    }

    @Override
    public RefreshToken deleteByUserId(String userId) {
        return refreshTokenRepository.deleteByUserId(userId);
    }
}
