package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.ServiceRequest;
import com.d3tec.template.d3tec.entity.Service;
import com.d3tec.template.d3tec.repository.ServiceRepository;
import com.d3tec.template.d3tec.service.ServiceService;
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
public class ServiceController {

    private final ServiceService serviceService;
    private final ServiceRepository serviceRepository;

    /* Public */
    @GetMapping("/services")
    @Operation(summary = "Listar todos os servicos")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Service>> listPublic() {
        return ResponseEntity.ok(serviceRepository.findAllByOrderByCreatedAtDesc());
    }

    @GetMapping("/services/{id}")
    @Operation(summary = "Buscar um servico pelo id")
    @SecurityRequirement(name = "")
    public ResponseEntity<Service> findPublic(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /* Admin */
    @GetMapping("/admin/services")
    @Operation(summary = "Listar servicos paginados (admin)")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Service>> listAll(Pageable pageable) {
        return ResponseEntity.ok(serviceService.findAll(pageable));
    }

    @GetMapping("/admin/services/{id}")
    @Operation(summary = "Buscar servico por ID (admin)")
    @Transactional(readOnly = true)
    public ResponseEntity<Service> findById(@PathVariable Long id) {
        return serviceService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/services")
    @Operation(summary = "Criar um novo servico")
    public ResponseEntity<Service> create(@RequestBody @Valid ServiceRequest request) {
        return ResponseEntity.ok(serviceService.create(request));
    }

    @PutMapping("/admin/services/{id}")
    @Operation(summary = "Editar um servico existente")
    public ResponseEntity<Service> update(@PathVariable Long id, @RequestBody @Valid ServiceRequest request) {
        return ResponseEntity.ok(serviceService.update(id, request));
    }

    @DeleteMapping("/admin/services/{id}")
    @Operation(summary = "Excluir um servico")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        serviceService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
