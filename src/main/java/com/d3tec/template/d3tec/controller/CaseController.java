package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.CaseRequest;
import com.d3tec.template.d3tec.entity.Case;
import com.d3tec.template.d3tec.service.CaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class CaseController {

    private final CaseService caseService;

    /* Public */
    @GetMapping("/cases")
    @Operation(summary = "Listar cases publicados paginados")
    @SecurityRequirement(name = "")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Case>> listPublic(Pageable pageable) {
        return ResponseEntity.ok(caseService.findPublished(pageable));
    }

    @GetMapping("/cases/featured")
    @Operation(summary = "Buscar o case em destaque")
    @SecurityRequirement(name = "")
    @Transactional(readOnly = true)
    public ResponseEntity<Case> findFeatured() {
        return caseService.findFeatured()
                .filter(Case::isExibirAoPublico)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/cases/{id}")
    @Operation(summary = "Buscar um case pelo id")
    @SecurityRequirement(name = "")
    @Transactional(readOnly = true)
    public ResponseEntity<Case> findPublic(@PathVariable Long id) {
        return caseService.findById(id)
                .filter(Case::isExibirAoPublico)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* Admin */
    @GetMapping("/admin/cases")
    @Operation(summary = "Listar todos os cases paginados (admin)")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Case>> listAll(Pageable pageable) {
        return ResponseEntity.ok(caseService.findAllPaginated(pageable));
    }

    @GetMapping("/admin/cases/{id}")
    @Operation(summary = "Buscar um case pelo id (admin)")
    @Transactional(readOnly = true)
    public ResponseEntity<Case> findById(@PathVariable Long id) {
        return caseService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/cases")
    @Operation(summary = "Criar um novo case")
    public ResponseEntity<Case> create(@RequestBody @Valid CaseRequest request) {
        return ResponseEntity.ok(caseService.create(request));
    }

    @PutMapping("/admin/cases/{id}")
    @Operation(summary = "Editar um case existente")
    public ResponseEntity<Case> update(@PathVariable Long id, @RequestBody @Valid CaseRequest request) {
        return ResponseEntity.ok(caseService.update(id, request));
    }

    @DeleteMapping("/admin/cases/{id}")
    @Operation(summary = "Excluir um case")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        caseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
