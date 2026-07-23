package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Post;
import com.d3tec.template.d3tec.entity.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByStatusOrderByDataPublicacaoDesc(PostStatus status);

    Optional<Post> findBySlugAndStatus(String slug, PostStatus status);

    boolean existsBySlug(String slug);
}
