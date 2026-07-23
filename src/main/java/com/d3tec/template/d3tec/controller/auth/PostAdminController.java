package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.dto.PostRequest;
import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

@RestController
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
@Tag(name = "Blog (Admin)", description = "Endpoints para gerenciamento de posts do blog")
public class PostAdminController {
    private final PostService postService;

    @PostMapping
    @Operation(summary = "Cria um novo post (rascunho)")
    public ResponseEntity<Post> create(@RequestBody @Valid PostRequest request) {
        return ResponseEntity.ok(postService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar um post existente")
    public ResponseEntity<Post> update(@PathVariable Long id, @RequestBody @Valid PostRequest request) {
        return ResponseEntity.ok(postService.update(id, request));
    }

    @PatchMapping("/{id}/publish")
    @Operation(summary = "Publicar um post")
    public ResponseEntity<Post> publish(@PathVariable Long id) {
        return ResponseEntity.ok(postService.publish(id));
    }

    @PatchMapping("/{id}/unpublish")
    @Operation(summary = "Despublicar um post (volta para rascunho)")
    public ResponseEntity<Post> unpublish(@PathVariable Long id) {
        return ResponseEntity.ok(postService.unpublish(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um post")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        postService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
