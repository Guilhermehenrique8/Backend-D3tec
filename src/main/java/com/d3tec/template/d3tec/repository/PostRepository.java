package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @EntityGraph(attributePaths = {"categoria", "tags"})
    List<Post> findByExibirAoPublicoTrueOrderByDataPublicacaoDesc();

    @EntityGraph(attributePaths = {"categoria", "tags"})
    Optional<Post> findBySlugAndExibirAoPublicoTrue(String slug);

    @EntityGraph(attributePaths = {"categoria", "tags"})
    List<Post> findAll();

    @EntityGraph(attributePaths = {"categoria", "tags"})
    Optional<Post> findById(Long id);

    boolean existsBySlug(String slug);
}
