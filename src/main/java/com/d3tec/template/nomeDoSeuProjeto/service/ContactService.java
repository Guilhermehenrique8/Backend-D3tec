package com.d3tec.template.nomeDoSeuProjeto.service;

import com.d3tec.template.nomeDoSeuProjeto.dto.ContactRequest;
import com.d3tec.template.nomeDoSeuProjeto.dto.GenericMessageResponse;
import com.d3tec.template.nomeDoSeuProjeto.email.service.ApplicationEmailService;
import com.d3tec.template.nomeDoSeuProjeto.entity.ContactMessage;
import com.d3tec.template.nomeDoSeuProjeto.repository.ContactMessageRepository;
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
        // honeypot preenchido = bot; responde sucesso mas não salva nem envia nada
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