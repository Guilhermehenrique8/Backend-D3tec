package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.TagRequest;
import com.d3tec.template.d3tec.entity.Tag;
import com.d3tec.template.d3tec.repository.TagRepository;
import com.d3tec.template.d3tec.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TagAdminController {

    private final TagService tagService;
    private final TagRepository tagRepository;

    @GetMapping("/tags")
    @Operation(summary = "Listar todas as tags (publico)")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Tag>> listPublic() {
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @GetMapping("/admin/tags")
    @Operation(summary = "Listar todas as tags (admin)")
    public ResponseEntity<List<Tag>> listAll() {
        return ResponseEntity.ok(tagRepository.findAll());
    }

    @PostMapping
    @Operation(summary = "Criar uma nova tag")
    public ResponseEntity<Tag> create(@RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar o nome de uma tag")
    public ResponseEntity<Tag> update(@PathVariable Long id, @RequestBody @Valid TagRequest request) {
        return ResponseEntity.ok(tagService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir uma tag")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        tagService.delete(id);
        return ResponseEntity.noContent().build();
    }
}