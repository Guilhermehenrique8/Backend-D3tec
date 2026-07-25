package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.ServiceRequest;
import com.d3tec.template.d3tec.repository.ServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository serviceRepository;

    public Optional<com.d3tec.template.d3tec.entity.Service> findById(Long id) {
        return serviceRepository.findById(id);
    }

    public Page<com.d3tec.template.d3tec.entity.Service> findAll(Pageable pageable) {
        return serviceRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public com.d3tec.template.d3tec.entity.Service create(ServiceRequest request) {
        var s = new com.d3tec.template.d3tec.entity.Service();
        populate(s, request);
        s.setCreatedAt(java.time.LocalDateTime.now());
        return serviceRepository.save(s);
    }

    public com.d3tec.template.d3tec.entity.Service update(Long id, ServiceRequest request) {
        var s = serviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Servico nao encontrado"));
        populate(s, request);
        return serviceRepository.save(s);
    }

    public void delete(Long id) {
        if (!serviceRepository.existsById(id)) {
            throw new IllegalArgumentException("Servico nao encontrado");
        }
        serviceRepository.deleteById(id);
    }

    private void populate(com.d3tec.template.d3tec.entity.Service s, ServiceRequest r) {
        s.setNome(r.getNome());
        s.setDescricaoCurta(r.getDescricaoCurta());
        s.setDescricaoDetalhada(r.getDescricaoDetalhada());
        s.setProblemasQueResolve(r.getProblemasQueResolve());
        s.setBeneficios(r.getBeneficios());
        s.setIcone(r.getIcone());
        s.setCtaTexto(r.getCtaTexto());
        s.setCtaLink(r.getCtaLink());
    }
}
