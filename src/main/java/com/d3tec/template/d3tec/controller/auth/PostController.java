package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.entity.PostStatus;
import com.d3tec.template.d3tec.repository.PostRepository;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Blog", description = "Endpoints pÃºblicos de consulta dos posts do blog da D3TEC")
public class PostController {

    private final PostRepository postRepository;

    @GetMapping
    @Operation(summary = "Listar todos os posts publicados, do mais recente para o mais antigo")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Post>> listAllPublished() {
        return ResponseEntity.ok(postRepository.findByStatusOrderByDataPublicacaoDesc(PostStatus.PUBLICADO));
    }

    @GetMapping("/{slug}")
    @Operation(summary = "Buscar um post publicado pelo slug (URL amigÃ¡vel)")
    @SecurityRequirement(name = "")
    public ResponseEntity<Post> findBySlug(@PathVariable String slug) {
        return postRepository.findBySlugAndStatus(slug, PostStatus.PUBLICADO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
