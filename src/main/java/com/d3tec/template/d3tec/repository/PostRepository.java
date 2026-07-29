package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p.id FROM Post p WHERE p.exibirAoPublico = true ORDER BY p.dataPublicacao DESC")
    Page<Long> findIdsByExibirAoPublicoTrue(Pageable pageable);

    @Query("SELECT p.id FROM Post p ORDER BY p.createdAt DESC")
    Page<Long> findAllIdsByOrderByCreatedAtDesc(Pageable pageable);

    @EntityGraph(attributePaths = {"categoria", "tags"})
    List<Post> findByIdInOrderByDataPublicacaoDesc(List<Long> ids);

    @EntityGraph(attributePaths = {"categoria", "tags"})
    List<Post> findByIdInOrderByCreatedAtDesc(List<Long> ids);

    @EntityGraph(attributePaths = {"categoria", "tags"})
    Optional<Post> findBySlugAndExibirAoPublicoTrue(String slug);

    boolean existsBySlug(String slug);

    long countByExibirAoPublicoTrue();

    @Query("SELECT p.id FROM Post p WHERE p.exibirAoPublico = true " +
            "AND (:search IS NULL OR p.titulo ILIKE '%' || :search || '%' " +
            "OR p.descricao ILIKE '%' || :search || '%') " +
            "AND (:categoriaId IS NULL OR p.categoria.id = :categoriaId) " +
            "ORDER BY p.dataPublicacao DESC")
    Page<Long> findIdsByFilters(@Param("search") String search, @Param("categoriaId") Long categoriaId, Pageable pageable);

    @Query("SELECT DISTINCT p.id, p.dataPublicacao FROM Post p JOIN p.tags t WHERE p.exibirAoPublico = true " +
            "AND t.id IN :tagIds " +
            "ORDER BY p.dataPublicacao DESC")
    Page<Object[]> findIdsByTags(@Param("tagIds") List<Long> tagIds, Pageable pageable);
}
