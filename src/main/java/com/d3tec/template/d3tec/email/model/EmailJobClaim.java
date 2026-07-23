package com.d3tec.template.d3tec.email.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmailJobClaim {
    EmailJob job;
    String claimToken;
}
