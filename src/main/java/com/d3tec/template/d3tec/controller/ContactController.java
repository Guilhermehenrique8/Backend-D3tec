package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.ContactRequest;
import com.d3tec.template.d3tec.dto.GenericMessageResponse;
import com.d3tec.template.d3tec.entity.ContactMessage;
import com.d3tec.template.d3tec.repository.ContactMessageRepository;
import com.d3tec.template.d3tec.service.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;
    private final ContactMessageRepository contactMessageRepository;

    /* Public */
    @PostMapping("/contact/sendMail")
    @Operation(summary = "Enviar mensagem de contato")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> sendMail(@RequestBody @Valid ContactRequest request) {
        return ResponseEntity.ok(contactService.send(request));
    }

    /* Admin */
    @GetMapping("/admin/contacts")
    @Operation(summary = "Listar mensagens de contato paginadas")
    public ResponseEntity<Page<ContactMessage>> listAll(Pageable pageable) {
        return ResponseEntity.ok(contactMessageRepository.findAllByOrderByCreatedAtDesc(pageable));
    }

    @GetMapping("/admin/contacts/{id}")
    @Operation(summary = "Buscar uma mensagem por ID")
    public ResponseEntity<ContactMessage> findById(@PathVariable Long id) {
        return contactMessageRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/admin/contacts/{id}")
    @Operation(summary = "Excluir uma mensagem")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactMessageRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
