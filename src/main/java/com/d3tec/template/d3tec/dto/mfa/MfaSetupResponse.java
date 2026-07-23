package com.d3tec.template.d3tec.dto.mfa;

import lombok.Data;

@Data
public class MfaSetupResponse {
    private boolean mfaEnabled;
    private String qrCodeDataUri;
}
