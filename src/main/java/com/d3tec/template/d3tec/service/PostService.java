package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.PostRequest;
import com.d3tec.template.d3tec.entity.Categoria;
import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.entity.Tag;
import com.d3tec.template.d3tec.repository.CategoriaRepository;
import com.d3tec.template.d3tec.repository.PostRepository;
import com.d3tec.template.d3tec.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final CategoriaRepository categoriaRepository;

    public Post create(PostRequest request) {
        Post post = new Post();
        post.setTitulo(request.getTitulo());
        post.setAutor(request.getAutor());
        post.setImagemCapa(request.getImagemCapa());
        post.setDescricao(request.getDescricao());
        post.setCategoria(resolveCategoria(request.getCategoriaId()));
        post.setTags(new HashSet<>(resolveTags(request.getTagIds())));
        post.setExibirAoPublico(request.isExibirAoPublico());
        post.setSlug(generateUniqueSlug(request.getTitulo()));
        post.setCreatedAt(LocalDateTime.now());

        if (request.isExibirAoPublico()) {
            post.setDataPublicacao(LocalDateTime.now());
        }

        return postRepository.save(post);
    }

    public Post update(Long id, PostRequest request) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post nao encontrado"));

        boolean estavaPublico = post.isExibirAoPublico();

        post.setTitulo(request.getTitulo());
        post.setAutor(request.getAutor());
        post.setImagemCapa(request.getImagemCapa());
        post.setDescricao(request.getDescricao());
        post.setCategoria(resolveCategoria(request.getCategoriaId()));
        post.setTags(new HashSet<>(resolveTags(request.getTagIds())));
        post.setExibirAoPublico(request.isExibirAoPublico());
        post.setUpdatedAt(LocalDateTime.now());

        if (!estavaPublico && request.isExibirAoPublico()) {
            post.setDataPublicacao(LocalDateTime.now());
        }

        return postRepository.save(post);
    }

    public void delete(Long id) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Post nao encontrado");
        }
        postRepository.deleteById(id);
    }

    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }

    public Page<Post> findPublished(Pageable pageable) {
        Page<Long> idsPage = postRepository.findIdsByExibirAoPublicoTrue(pageable);
        List<Post> posts = postRepository.findByIdInOrderByDataPublicacaoDesc(idsPage.getContent());
        return new PageImpl<>(posts, pageable, idsPage.getTotalElements());
    }

    public Page<Post> findAllPaginated(Pageable pageable) {
        Page<Long> idsPage = postRepository.findAllIdsByOrderByCreatedAtDesc(pageable);
        List<Post> posts = postRepository.findByIdInOrderByCreatedAtDesc(idsPage.getContent());
        return new PageImpl<>(posts, pageable, idsPage.getTotalElements());
    }

    private Categoria resolveCategoria(Long categoriaId) {
        if (categoriaId == null) {
            return null;
        }
        return categoriaRepository.findById(categoriaId)
                .orElseThrow(() -> new IllegalArgumentException("Categoria nao encontrada"));
    }

    private List<Tag> resolveTags(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return List.of();
        }
        return tagRepository.findAllById(tagIds);
    }

    private String generateUniqueSlug(String titulo) {
        String base = toSlug(titulo);
        String slug = base;
        int suffix = 1;

        while (postRepository.existsBySlug(slug)) {
            suffix++;
            slug = base + "-" + suffix;
        }

        return slug;
    }

    private String toSlug(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        String withoutAccents = normalized.replaceAll("\\p{M}", "");
        String lower = withoutAccents.toLowerCase();
        String onlyAlphanumericAndSpaces = lower.replaceAll("[^a-z0-9\\s-]", "");
        return onlyAlphanumericAndSpaces.trim().replaceAll("\\s+", "-");
    }
}
