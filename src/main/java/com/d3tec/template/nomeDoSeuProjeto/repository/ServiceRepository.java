package com.d3tec.template.nomeDoSeuProjeto.repository;

import com.d3tec.template.nomeDoSeuProjeto.entity.Service;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
}