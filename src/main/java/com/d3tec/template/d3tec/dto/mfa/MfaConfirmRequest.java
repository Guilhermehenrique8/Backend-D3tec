package com.d3tec.template.d3tec.dto.mfa;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class MfaConfirmRequest {
    @NotBlank
    private String code;
}
