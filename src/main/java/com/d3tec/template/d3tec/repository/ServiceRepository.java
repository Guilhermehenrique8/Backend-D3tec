package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {

    Page<Service> findAllByOrderByCreatedAtDesc(Pageable pageable);

    List<Service> findAllByOrderByCreatedAtDesc();
}
