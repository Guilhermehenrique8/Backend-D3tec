package com.d3tec.template.d3tec.service.auth;

import com.d3tec.template.d3tec.dto.LoginResponse;
import com.d3tec.template.d3tec.dto.RefreshTokenCreationDto;
import com.d3tec.template.d3tec.dto.mfa.MfaDisableRequest;
import com.d3tec.template.d3tec.dto.mfa.MfaSetupResponse;
import com.d3tec.template.d3tec.dto.mfa.MfaVerifyRequest;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.exception.exceptions.ApiException;
import com.d3tec.template.d3tec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class MfaService {

    private final JwtDecoder jwtDecoder;
    private final MfaTokenManager mfaTokenManager;
    private final UserRepository userRepository;
    private final AcessTokenService acessTokenService;
    private final RefreshTokenService refreshTokenService;
    private final MfaSecretProtectionService mfaSecretProtectionService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${jwt.token.expires.in}")
    private Long expiresIn;

    @Transactional
    public MfaSetupResponse mfaSetupForUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadCredentialsException("UsuÃ¡rio nÃ£o encontrado"));

        if (user.isMfaEnabled()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "UsuÃ¡rio ja possui o mfa habilitado!");
        }

        // se nÃ£o tem secret por algum motivo, gere
        if (user.getSecret() == null || user.getSecret().isBlank()) {
            user.setSecret(mfaSecretProtectionService.protect(mfaTokenManager.generateSecretKey()));
        } else {
            migrateSecretIfNeeded(user);
        }

        MfaSetupResponse resp = new MfaSetupResponse();
        resp.setMfaEnabled(user.isMfaEnabled());
        resp.setQrCodeDataUri(mfaTokenManager.generateQrCode(user.getEmail(), revealSecret(user)));

        return resp;
    }

    @Transactional
    public void confirmMfa(Long userId, String code) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadCredentialsException("UsuÃ¡rio nÃ£o encontrado"));

        if (user.isMfaEnabled()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "UsuÃ¡rio ja possui o mfa habilitado!");
        }

        if (user.getSecret() == null || user.getSecret().isBlank()) {
            throw new BadCredentialsException("MFA nÃ£o iniciado");
        }

        String rawSecret = revealSecret(user);
        migrateSecretIfNeeded(user);

        if (!mfaTokenManager.verifyTotp(code, rawSecret)) {
            throw new BadCredentialsException("CÃ³digo MFA invÃ¡lido!");
        }

        user.setMfaEnabled(true);
        refreshTokenService.revokeAllByUser(user);
    }

    @Transactional
    public LoginResponse verifyMfa(MfaVerifyRequest req) {
        Jwt jwt = null;
        try{
            jwt = jwtDecoder.decode(req.getMfaToken());
        } catch (Exception e) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Token MFA invÃ¡lido ou expirado");
        }

        // valida claims
        if (!"mfa_challenge".equals(jwt.getClaimAsString("typ")) ||
                !"pending".equals(jwt.getClaimAsString("mfa"))) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Token MFA invÃ¡lido!");
        }

        Long userId = Long.valueOf(jwt.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Token MFA invÃ¡lido!"));

        if (!user.isMfaEnabled()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "MFA nÃ£o habilitado!");
        }

        String rawSecret = revealSecret(user);
        migrateSecretIfNeeded(user);

        if (!mfaTokenManager.verifyTotp(req.getMfaCode(), rawSecret)) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "CÃ³digo MFA invÃ¡lido!");
        }

        // emite JWT final
        var jwtFinal = acessTokenService.getAcessToken(user, true);
        RefreshTokenCreationDto refreshTokenDto = refreshTokenService.create(user, Duration.ofDays(7), true);

        LoginResponse resp = new LoginResponse();
        resp.setAuthenticated(true);
        resp.setMfaRequired(false);
        resp.setToken(jwtFinal);
        resp.setRefreshToken(refreshTokenDto.getRawToken());
        resp.setExpiresInSeconds(expiresIn);
        return resp;
    }

    @Transactional
    public void disableMfa(Long userId, MfaDisableRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BadCredentialsException("UsuÃ¡rio nÃ£o encontrado"));

        if (!user.isMfaEnabled()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "MFA nÃ£o estÃ¡ habilitado para este usuÃ¡rio.");
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new BadCredentialsException("Senha atual invÃ¡lida.");
        }

        String rawSecret = revealSecret(user);
        if (!mfaTokenManager.verifyTotp(request.getCode(), rawSecret)) {
            throw new BadCredentialsException("CÃ³digo MFA invÃ¡lido!");
        }

        user.setMfaEnabled(false);
        user.setSecret(null);
        refreshTokenService.revokeAllByUser(user);
    }

    private String revealSecret(User user) {
        if (!StringUtils.hasText(user.getSecret())) {
            throw new BadCredentialsException("MFA nÃ£o iniciado");
        }

        return mfaSecretProtectionService.reveal(user.getSecret());
    }

    private void migrateSecretIfNeeded(User user) {
        if (!mfaSecretProtectionService.requiresMigration(user.getSecret())) {
            return;
        }

        user.setSecret(mfaSecretProtectionService.protect(user.getSecret()));
    }
}
