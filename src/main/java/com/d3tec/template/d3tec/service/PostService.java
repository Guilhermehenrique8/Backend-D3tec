package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.PostRequest;
import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.entity.PostStatus;
import com.d3tec.template.d3tec.entity.Tag;
import com.d3tec.template.d3tec.repository.PostRepository;
import com.d3tec.template.d3tec.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;

    public Post create(PostRequest request) {
        Post post = new Post();
        post.setTitulo(request.getTitulo());
        post.setAutor(request.getAutor());
        post.setImagemCapa(request.getImagemCapa());
        post.setResumo(request.getResumo());
        post.setConteudo(request.getConteudo());
        post.setTag(resolveTag(request.getTagId()));
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
        post.setTag(resolveTag(request.getTagId()));
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

    private Tag resolveTag(Long tagId) {
        if (tagId == null) {
            return null;
        }
        return tagRepository.findById(tagId)
                .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada"));
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