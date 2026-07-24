package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByExibirAoPublicoTrueOrderByDataPublicacaoDesc();

    Optional<Post> findBySlugAndExibirAoPublicoTrue(String slug);

    boolean existsBySlug(String slug);
}
