package com.d3tec.template.d3tec.dto.mfa;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MfaDisableRequest {

    @NotBlank
    private String currentPassword;

    @NotBlank
    private String code;
}
