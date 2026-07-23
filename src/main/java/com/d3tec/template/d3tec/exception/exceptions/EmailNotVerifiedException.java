package com.d3tec.template.d3tec.exception.exceptions;

import org.springframework.http.HttpStatus;

public class EmailNotVerifiedException extends ApiException {
    public EmailNotVerifiedException(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
