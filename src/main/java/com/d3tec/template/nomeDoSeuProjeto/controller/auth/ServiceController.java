package com.d3tec.template.nomeDoSeuProjeto.controller.auth;

import com.d3tec.template.nomeDoSeuProjeto.entity.Service;
import com.d3tec.template.nomeDoSeuProjeto.repository.ServiceRepository;
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
@RequestMapping("/services")
@RequiredArgsConstructor
@Tag(name = "Serviços", description = "Endpoints públicos de consulta dos serviços da D3TEC")
public class ServiceController {

    private final ServiceRepository serviceRepository;

    @GetMapping
    @Operation(summary = "Listar todos os serviços")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Service>> listAll() {
        return ResponseEntity.ok(serviceRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um serviço pelo id")
    @SecurityRequirement(name = "")
    public ResponseEntity<Service> findById(@PathVariable Long id) {
        return serviceRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}