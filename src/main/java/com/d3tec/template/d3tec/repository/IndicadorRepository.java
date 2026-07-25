package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Indicador;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IndicadorRepository extends JpaRepository<Indicador, Long> {
    List<Indicador> findAllByOrderByNomeAsc();
    Page<Indicador> findAllByOrderByNomeAsc(Pageable pageable);
}
