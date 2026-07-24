package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.entity.Categoria;
import com.d3tec.template.d3tec.repository.CategoriaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/categorias")
@RequiredArgsConstructor
@Tag(name = "Categorias (Admin)", description = "CRUD de categorias para posts")
public class CategoriaAdminController {

    private final CategoriaRepository categoriaRepository;

    @GetMapping
    @Operation(summary = "Listar todas as categorias")
    public ResponseEntity<List<Categoria>> listAll() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma categoria por ID")
    public ResponseEntity<Categoria> findById(@PathVariable Long id) {
        return categoriaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar uma categoria")
    public ResponseEntity<Categoria> create(@RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = new Categoria();
        categoria.setNome(request.getNome());
        categoria.setSlug(toSlug(request.getNome()));
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma categoria")
    public ResponseEntity<Categoria> update(@PathVariable Long id, @RequestBody @Valid CategoriaRequest request) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada"));
        categoria.setNome(request.getNome());
        categoria.setSlug(toSlug(request.getNome()));
        return ResponseEntity.ok(categoriaRepository.save(categoria));
    }

    @DeleteMapping("/{id}")
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
    @jakarta.validation.constraints.NotBlank
    @jakarta.validation.constraints.Size(max = 100)
    private String nome;
}
