package com.d3tec.template.d3tec.service.auth;

public interface MfaSecretProtectionService {

    String protect(String rawSecret);

    String reveal(String storedSecret);

    boolean isProtectionEnabled();

    default boolean requiresMigration(String storedSecret) {
        return false;
    }
}
