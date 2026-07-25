package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.PartnerRequest;
import com.d3tec.template.d3tec.entity.Partner;
import com.d3tec.template.d3tec.service.PartnerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PartnerController {

    private final PartnerService partnerService;

    /* Public */
    @GetMapping("/partners")
    @Operation(summary = "Listar parceiros ativos")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Partner>> listActive() {
        return ResponseEntity.ok(partnerService.findActive());
    }

    /* Admin */
    @GetMapping("/admin/partners")
    @Operation(summary = "Listar parceiros paginados (admin)")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Partner>> listAll(Pageable pageable) {
        return ResponseEntity.ok(partnerService.findAll(pageable));
    }

    @GetMapping("/admin/partners/{id}")
    @Operation(summary = "Buscar parceiro por ID")
    @Transactional(readOnly = true)
    public ResponseEntity<Partner> findById(@PathVariable Long id) {
        return partnerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/partners")
    @Operation(summary = "Criar parceiro")
    public ResponseEntity<Partner> create(@RequestBody @Valid PartnerRequest request) {
        return ResponseEntity.ok(partnerService.create(request));
    }

    @PutMapping("/admin/partners/{id}")
    @Operation(summary = "Editar parceiro")
    public ResponseEntity<Partner> update(@PathVariable Long id, @RequestBody @Valid PartnerRequest request) {
        return ResponseEntity.ok(partnerService.update(id, request));
    }

    @DeleteMapping("/admin/partners/{id}")
    @Operation(summary = "Excluir parceiro")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        partnerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
