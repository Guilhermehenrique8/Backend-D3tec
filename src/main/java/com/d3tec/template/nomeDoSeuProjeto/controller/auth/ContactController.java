package com.d3tec.template.nomeDoSeuProjeto.controller.auth;

import com.d3tec.template.nomeDoSeuProjeto.dto.ContactRequest;
import com.d3tec.template.nomeDoSeuProjeto.dto.GenericMessageResponse;
import com.d3tec.template.nomeDoSeuProjeto.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contact")
@RequiredArgsConstructor
@Tag(name = "Contato", description = "Endpoint público do formulário de contato do site")
public class ContactController {

    private final ContactService contactService;

    @PostMapping("/sendMail")
    @Operation(summary = "Enviar mensagem de contato", description = "Recebe os dados do formulário de contato e envia por e-mail para a equipe.")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> sendMail(@RequestBody @Valid ContactRequest request) {
        return ResponseEntity.ok(contactService.send(request));
    }
}