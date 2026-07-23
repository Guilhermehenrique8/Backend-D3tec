package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {

    List<Case> findByPublicadoTrue();
}
