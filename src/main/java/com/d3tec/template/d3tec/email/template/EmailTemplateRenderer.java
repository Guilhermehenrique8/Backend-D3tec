package com.d3tec.template.d3tec.email.template;

import com.d3tec.template.d3tec.email.model.EmailType;

import java.util.Map;

public interface EmailTemplateRenderer {
    RenderedEmailTemplate render(EmailType emailType, Map<String, Object> model);
}
