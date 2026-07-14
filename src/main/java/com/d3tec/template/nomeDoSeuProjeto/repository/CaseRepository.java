package com.d3tec.template.nomeDoSeuProjeto.repository;

import com.d3tec.template.nomeDoSeuProjeto.entity.Case;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaseRepository extends JpaRepository<Case, Long> {

    List<Case> findByPublicadoTrue();
}