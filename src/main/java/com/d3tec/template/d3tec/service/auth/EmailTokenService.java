package com.d3tec.template.d3tec.service.auth;

import com.d3tec.template.d3tec.entity.EmailToken;
import com.d3tec.template.d3tec.entity.EmailTokenType;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.exception.exceptions.ApiException;
import com.d3tec.template.d3tec.repository.EmailTokenRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class EmailTokenService {

    private final EmailTokenRepository emailTokenRepository;
    private final SecureRandom secureRandom;

    public String create(User user, EmailTokenType type, Duration duration) {
        emailTokenRepository.deleteAllByUserAndType(user, type);

        String rawToken = generateSecureToken();
        EmailToken emailToken = new EmailToken();
        emailToken.setUser(user);
        emailToken.setType(type);
        emailToken.setTokenHash(hashToken(rawToken));
        emailToken.setCreatedAt(Instant.now());
        emailToken.setExpiresAt(Instant.now().plus(duration));
        emailToken.setConsumed(false);

        emailTokenRepository.save(emailToken);
        return rawToken;
    }

    public User consume(String rawToken, EmailTokenType type) {
        String hash = hashToken(rawToken);

        EmailToken token = emailTokenRepository.findByTokenHashAndType(hash, type)
                .orElseThrow(() -> new BadCredentialsException("Token invÃ¡lido"));

        if (token.isConsumed()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token jÃ¡ utilizado");
        }

        if (token.getExpiresAt().isBefore(Instant.now())) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token expirado");
        }

        token.setConsumed(true);
        token.setConsumedAt(Instant.now());
        emailTokenRepository.save(token);

        return token.getUser();
    }

    private String generateSecureToken() {
        byte[] bytes = new byte[48];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashToken(String rawToken) {
        return DigestUtils.sha256Hex(rawToken);
    }
}
