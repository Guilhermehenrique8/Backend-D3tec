package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.dto.PostRequest;
import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.repository.PostRepository;
import com.d3tec.template.d3tec.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
@Tag(name = "Blog (Admin)", description = "Endpoints protegidos de gerenciamento do blog")
public class PostAdminController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping
    @Operation(summary = "Listar todos os posts")
    public ResponseEntity<List<Post>> listAll() {
        return ResponseEntity.ok(postRepository.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um post pelo id")
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        return postRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Criar um novo post")
    public ResponseEntity<Post> create(@RequestBody @Valid PostRequest request) {
        return ResponseEntity.ok(postService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar um post existente")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody @Valid PostRequest request) {
        return ResponseEntity.ok(postService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um post")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
