package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.dto.PostRequest;
import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/posts")
@RequiredArgsConstructor
@Tag(name = "Blog (Admin)", description = "Endpoints protegidos de gerenciamento do blog")
public class PostAdminController {

    private final PostService postService;

    @GetMapping
    @Operation(summary = "Listar todos os posts paginados")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Post>> listAll(Pageable pageable) {
        return ResponseEntity.ok(postService.findAllPaginated(pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um post pelo id")
    @Transactional(readOnly = true)
    public ResponseEntity<Post> findById(@PathVariable Long id) {
        return postService.findById(id)
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
