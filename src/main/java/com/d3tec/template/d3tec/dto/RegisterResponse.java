package com.d3tec.template.d3tec.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class RegisterResponse {
    String message;
    String email;
    boolean verificationRequired;
}
