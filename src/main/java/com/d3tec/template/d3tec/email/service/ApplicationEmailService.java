package com.d3tec.template.d3tec.email.service;

import com.d3tec.template.d3tec.email.config.MailProperties;
import com.d3tec.template.d3tec.email.model.EmailType;
import com.d3tec.template.d3tec.email.model.TransactionalEmail;
import com.d3tec.template.d3tec.email.queue.EmailQueuePort;
import com.d3tec.template.d3tec.email.template.EmailTemplateRenderer;
import com.d3tec.template.d3tec.email.template.RenderedEmailTemplate;
import com.d3tec.template.d3tec.entity.ContactMessage;
import com.d3tec.template.d3tec.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class ApplicationEmailService {

    private final EmailTemplateRenderer templateRenderer;
    private final EmailQueuePort emailQueuePort;
    private final MailProperties mailProperties;

    @Value("${spring.application.name}")
    private String appName;

    public void sendEmailVerification(User user, String rawToken) {
        queueTemplateEmail(
                user.getEmail(),
                EmailType.EMAIL_VERIFICATION,
                Map.of(
                        "verificationUrl", mailProperties.getBaseUrl() + "/auth/verify-email?token=" + rawToken,
                        "appName", appName
                ),
                "verify:" + user.getId() + ":" + rawToken
        );
    }

    public void sendForgotPassword(User user, String rawToken) {
        queueTemplateEmail(
                user.getEmail(),
                EmailType.PASSWORD_RESET,
                Map.of(
                        "resetUrl", mailProperties.getBaseUrl() + "/auth/reset-password?token=" + rawToken,
                        "appName", appName
                ),
                "forgot:" + user.getId() + ":" + rawToken
        );
    }

    private void queueTemplateEmail(
            String recipient,
            EmailType type,
            Map<String, Object> model,
            String idempotencyKey
    ) {
        RenderedEmailTemplate template = templateRenderer.render(type, model);
        emailQueuePort.enqueue(TransactionalEmail.builder()
                .to(recipient)
                .subject(template.getSubject())
                .htmlBody(template.getHtmlBody())
                .textBody(template.getTextBody())
                .type(type)
                .idempotencyKey(idempotencyKey)
                .build());
    }
    public void sendContactNotification(ContactMessage message, String notificationEmail) {
        queueTemplateEmail(
                notificationEmail,
                EmailType.CONTACT_FORM,
                Map.of(
                        "nome", message.getNome(),
                        "email", message.getEmail(),
                        "telefone", message.getTelefone() == null ? "-" : message.getTelefone(),
                        "empresa", message.getEmpresa() == null ? "-" : message.getEmpresa(),
                        "assunto", message.getAssunto(),
                        "mensagem", message.getMensagem(),
                        "appName", appName
                ),
                "contact:" + message.getId()
        );
    }
}
