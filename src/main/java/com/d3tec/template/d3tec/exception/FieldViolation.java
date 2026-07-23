package com.d3tec.template.d3tec.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FieldViolation {
    private String field;
    private String message;
}
