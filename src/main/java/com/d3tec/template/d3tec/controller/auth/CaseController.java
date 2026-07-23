package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.entity.Case;
import com.d3tec.template.d3tec.repository.CaseRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cases")
@RequiredArgsConstructor
@Tag(name = "Cases", description = "Endpoints pÃºblicos de consulta dos cases da D3TEC")

public class CaseController {
    
    private final CaseRepository caseRepository;

    @GetMapping
    @Operation(summary = "Listar todos os cases publicados")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Case>> listAllPublished() {
        return ResponseEntity.ok(caseRepository.findByPublicadoTrue());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um case publicado pelo id")
    @SecurityRequirement(name = "")
    public ResponseEntity<Case> findById(@PathVariable Long id) {
        return caseRepository.findById(id)
                .filter(Case::isPublicado)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
