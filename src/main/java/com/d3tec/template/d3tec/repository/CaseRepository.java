package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Case;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {

    @Query("SELECT c.id FROM Case c WHERE c.exibirAoPublico = true ORDER BY c.createdAt DESC")
    Page<Long> findIdsByExibirAoPublicoTrue(Pageable pageable);

    @Query("SELECT c.id FROM Case c ORDER BY c.createdAt DESC")
    Page<Long> findAllIds(Pageable pageable);

    @EntityGraph(attributePaths = {"tags"})
    List<Case> findByIdInOrderByCreatedAtDesc(List<Long> ids);

    @EntityGraph(attributePaths = {"tags"})
    Optional<Case> findById(Long id);
}
