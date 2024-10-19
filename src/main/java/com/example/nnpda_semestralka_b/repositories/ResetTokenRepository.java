package com.example.nnpda_semestralka_b.repositories;

import com.example.nnpda_semestralka_b.entity.ResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetTokenRepository extends JpaRepository<ResetToken, Integer> {
    public Optional<ResetToken> getResetTokenByToken(String token);
}
