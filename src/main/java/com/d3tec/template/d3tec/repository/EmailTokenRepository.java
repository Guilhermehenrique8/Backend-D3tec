package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.EmailToken;
import com.d3tec.template.d3tec.entity.EmailTokenType;
import com.d3tec.template.d3tec.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByTokenHashAndType(String tokenHash, EmailTokenType type);
    void deleteAllByUserAndType(User user, EmailTokenType type);
}
