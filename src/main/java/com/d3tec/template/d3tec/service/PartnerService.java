package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.PartnerRequest;
import com.d3tec.template.d3tec.entity.Partner;
import com.d3tec.template.d3tec.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public List<Partner> findActive() {
        return partnerRepository.findByAtivoTrueAndAutorizacaoExibicaoTrueOrderByNomeAsc();
    }

    public Optional<Partner> findById(Long id) {
        return partnerRepository.findById(id);
    }

    public Page<Partner> findAll(Pageable pageable) {
        return partnerRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    public Partner create(PartnerRequest request) {
        Partner p = new Partner();
        populate(p, request);
        p.setCreatedAt(LocalDateTime.now());
        return partnerRepository.save(p);
    }

    public Partner update(Long id, PartnerRequest request) {
        Partner p = partnerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parceiro nao encontrado"));
        populate(p, request);
        p.setUpdatedAt(LocalDateTime.now());
        return partnerRepository.save(p);
    }

    public void delete(Long id) {
        if (!partnerRepository.existsById(id)) {
            throw new IllegalArgumentException("Parceiro nao encontrado");
        }
        partnerRepository.deleteById(id);
    }

    private void populate(Partner p, PartnerRequest r) {
        p.setNome(r.getNome());
        p.setLogo(r.getLogo());
        p.setLink(r.getLink());
        p.setTipo(r.getTipo());
        p.setAtivo(r.isAtivo());
        p.setAutorizacaoExibicao(r.isAutorizacaoExibicao());
    }
}
