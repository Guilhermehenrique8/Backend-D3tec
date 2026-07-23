package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.ContactRequest;
import com.d3tec.template.d3tec.dto.GenericMessageResponse;
import com.d3tec.template.d3tec.email.service.ApplicationEmailService;
import com.d3tec.template.d3tec.entity.ContactMessage;
import com.d3tec.template.d3tec.repository.ContactMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactService {

    private final ContactMessageRepository repository;
    private final ApplicationEmailService emailService;

    @Value("${app.contact.notification-email}")
    private String notificationEmail;

    public GenericMessageResponse send(ContactRequest request) {
        // honeypot preenchido = bot; responde sucesso mas nÃ£o salva nem envia nada
        if (request.getWebsite() != null && !request.getWebsite().isBlank()) {
            return new GenericMessageResponse("Mensagem enviada com sucesso.");
        }

        ContactMessage message = new ContactMessage();
        message.setNome(request.getNome());
        message.setEmail(request.getEmail());
        message.setTelefone(request.getTelefone());
        message.setEmpresa(request.getEmpresa());
        message.setAssunto(request.getAssunto());
        message.setMensagem(request.getMensagem());
        message.setCreatedAt(LocalDateTime.now());

        message = repository.save(message);
        emailService.sendContactNotification(message, notificationEmail);

        return new GenericMessageResponse("Mensagem enviada com sucesso. Em breve entraremos em contato.");
    }
}
