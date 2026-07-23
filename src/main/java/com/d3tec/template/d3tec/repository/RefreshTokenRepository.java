package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.RefreshToken;
import com.d3tec.template.d3tec.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByTokenHash(String tokenHash);
    void deleteAllByUser(User user);
}
