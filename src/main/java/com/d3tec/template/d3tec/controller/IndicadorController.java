package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.IndicadorRequest;
import com.d3tec.template.d3tec.entity.Indicador;
import com.d3tec.template.d3tec.service.IndicadorService;
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

@RestController @RequiredArgsConstructor
public class IndicadorController {
    private final IndicadorService service;

    @GetMapping("/indicadores")
    @Operation(summary = "Listar indicadores") @SecurityRequirement(name = "")
    public ResponseEntity<List<Indicador>> listPublic() { return ResponseEntity.ok(service.findAll()); }

    @GetMapping("/admin/indicadores")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Indicador>> listAll(Pageable p) { return ResponseEntity.ok(service.findAllPaginated(p)); }

    @GetMapping("/admin/indicadores/{id}") @Transactional(readOnly = true)
    public ResponseEntity<Indicador> findById(@PathVariable Long id) {
        return service.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/indicadores")
    public ResponseEntity<Indicador> create(@RequestBody @Valid IndicadorRequest r) { return ResponseEntity.ok(service.create(r)); }

    @PutMapping("/admin/indicadores/{id}")
    public ResponseEntity<Indicador> update(@PathVariable Long id, @RequestBody @Valid IndicadorRequest r) { return ResponseEntity.ok(service.update(id, r)); }

    @DeleteMapping("/admin/indicadores/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) { service.delete(id); return ResponseEntity.noContent().build(); }
}
