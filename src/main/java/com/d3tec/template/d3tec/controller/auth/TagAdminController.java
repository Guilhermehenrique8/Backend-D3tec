package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.dto.TagRequest;
import com.d3tec.template.d3tec.entity.Tag;
import com.d3tec.template.d3tec.repository.TagRepository;
import com.d3tec.template.d3tec.service.TagService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/tags")
@RequiredArgsConstructor
public class TagAdminController {

    private final TagService tagService;
    private final TagRepository tagRepository;

    @GetMapping
    @Operation(summary = "Listar todas as tags")
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