package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.IndicadorRequest;
import com.d3tec.template.d3tec.entity.Indicador;
import com.d3tec.template.d3tec.repository.IndicadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor
public class IndicadorService {
    private final IndicadorRepository repo;

    public List<Indicador> findAll() { return repo.findAllByOrderByNomeAsc(); }
    public Optional<Indicador> findById(Long id) { return repo.findById(id); }
    public Page<Indicador> findAllPaginated(Pageable p) { return repo.findAllByOrderByNomeAsc(p); }

    public Indicador create(IndicadorRequest r) {
        var i = new Indicador(); populate(i, r); i.setCreatedAt(LocalDateTime.now()); return repo.save(i);
    }
    public Indicador update(Long id, IndicadorRequest r) {
        var i = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("Nao encontrado"));
        populate(i, r); i.setUpdatedAt(LocalDateTime.now()); return repo.save(i);
    }
    public void delete(Long id) { repo.deleteById(id); }
    private void populate(Indicador i, IndicadorRequest r) {
        i.setNome(r.getNome()); i.setValor(r.getValor()); i.setDescricao(r.getDescricao());
    }
}
