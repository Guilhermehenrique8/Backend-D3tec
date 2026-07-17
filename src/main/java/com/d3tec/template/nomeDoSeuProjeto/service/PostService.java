package com.d3tec.template.nomeDoSeuProjeto.service;

import com.d3tec.template.nomeDoSeuProjeto.dto.PostRequest;
import com.d3tec.template.nomeDoSeuProjeto.entity.Post;
import com.d3tec.template.nomeDoSeuProjeto.entity.PostStatus;
import com.d3tec.template.nomeDoSeuProjeto.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Post create(PostRequest request) {
        Post post = new Post();
        post.setTitulo(request.getTitulo());
        post.setAutor(request.getAutor());
        post.setImagemCapa(request.getImagemCapa());
        post.setResumo(request.getResumo());
        post.setConteudo(request.getConteudo());
        post.setCategoria(request.getCategoria());
        post.setSlug(generateUniqueSlug(request.getTitulo()));
        post.setStatus(PostStatus.RASCUNHO);
        post.setCreatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public Post update(Long id, PostRequest request) {
    Post post = postRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Post não encontrado"));

    post.setTitulo(request.getTitulo());
    post.setAutor(request.getAutor());
    post.setImagemCapa(request.getImagemCapa());
    post.setResumo(request.getResumo());
    post.setConteudo(request.getConteudo());
    post.setCategoria(request.getCategoria());
    post.setUpdatedAt(LocalDateTime.now());

    return postRepository.save(post);
    }

    public Post publish(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado"));

        post.setStatus(PostStatus.PUBLICADO);
        post.setDataPublicacao(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public Post unpublish(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post não encontrado"));

        post.setStatus(PostStatus.RASCUNHO);
        post.setUpdatedAt(LocalDateTime.now());

        return postRepository.save(post);
    }

    public void delete(Long id) {
    if (!postRepository.existsById(id)) {
        throw new IllegalArgumentException("Post não encontrado");
    }
    postRepository.deleteById(id);
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