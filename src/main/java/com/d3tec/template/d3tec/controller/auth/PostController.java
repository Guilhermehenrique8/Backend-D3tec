package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.repository.PostRepository;
import com.d3tec.template.d3tec.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Blog", description = "Endpoints publicos de consulta dos posts do blog da D3TEC")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;

    @GetMapping
    @Operation(summary = "Listar posts publicados paginados")
    @SecurityRequirement(name = "")
    @Transactional(readOnly = true)
    public ResponseEntity<Page<Post>> listAllPublished(Pageable pageable) {
        return ResponseEntity.ok(postService.findPublished(pageable));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Buscar um post publicado pelo slug")
    @SecurityRequirement(name = "")
    @Transactional(readOnly = true)
    public ResponseEntity<Post> findBySlug(@PathVariable String slug) {
        return postRepository.findBySlugAndExibirAoPublicoTrue(slug)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
