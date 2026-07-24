package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    boolean existsByNome(String nome);
}