package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.entity.Categoria;
import com.d3tec.template.d3tec.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias", description = "Listagem publica de categorias")
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "Listar todas as categorias")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Categoria>> listAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }
}
