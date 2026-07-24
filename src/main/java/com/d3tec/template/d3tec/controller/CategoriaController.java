package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.entity.Categoria;
import com.d3tec.template.d3tec.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;

    /* Public */
    @GetMapping("/categorias")
    @Operation(summary = "Listar todas as categorias")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Categoria>> listPublic() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    /* Admin */
    @GetMapping("/admin/categorias")
    @Operation(summary = "Listar todas as categorias (admin)")
    public ResponseEntity<List<Categoria>> listAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping("/admin/categorias/{id}")
    @Operation(summary = "Buscar uma categoria por ID")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/admin/categorias")
    @Operation(summary = "Criar uma categoria")
    public ResponseEntity<Categoria> create(@RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.getNome());
        categoria.setSlug(toSlug(request.getNome()));
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @PutMapping("/admin/categorias/{id}")
    @Operation(summary = "Atualizar uma categoria")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada"));
        categoria.setNome(request.getNome());
        categoria.setSlug(toSlug(request.getNome()));
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @DeleteMapping("/admin/categorias/{id}")
    @Operation(summary = "Excluir uma categoria")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoriaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String toSlug(String text) {
        return java.text.Normalizer.normalize(text, java.text.Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim()
                .replaceAll("\\s+", "-");
    }
}

@Data
class CategoriaRequest {
    @NotBlank
    @Size(max = 100)
    private String nome;
}
