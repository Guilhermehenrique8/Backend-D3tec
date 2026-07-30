package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.CaseRequest;
import com.d3tec.template.d3tec.entity.Case;
import com.d3tec.template.d3tec.entity.Tag;
import com.d3tec.template.d3tec.repository.CaseRepository;
import com.d3tec.template.d3tec.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CaseService {

    private final CaseRepository caseRepository;
    private final TagRepository tagRepository;

    public Optional<Case> findById(Long id) {
        return caseRepository.findById(id);
    }

    public Optional<Case> findFeatured() {
        return caseRepository.findByFeaturedTrue();
    }

    public Page<Case> findPublished(Pageable pageable) {
        Page<Long> idsPage = caseRepository.findIdsByExibirAoPublicoTrue(pageable);
        List<Case> cases = caseRepository.findByIdInOrderByCreatedAtDesc(idsPage.getContent());
        return new PageImpl<>(cases, pageable, idsPage.getTotalElements());
    }

    public Page<Case> findAllPaginated(Pageable pageable) {
        Page<Long> idsPage = caseRepository.findAllIds(pageable);
        List<Case> cases = caseRepository.findByIdInOrderByCreatedAtDesc(idsPage.getContent());
        return new PageImpl<>(cases, pageable, idsPage.getTotalElements());
    }

    public Case create(CaseRequest request) {
        Case c = new Case();
        populate(c, request);
        c.setCreatedAt(LocalDateTime.now());
        return caseRepository.save(c);
    }

    public Case update(Long id, CaseRequest request) {
        Case c = caseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Case nao encontrado"));
        populate(c, request);
        c.setUpdatedAt(LocalDateTime.now());
        return caseRepository.save(c);
    }

    public void delete(Long id) {
        if (!caseRepository.existsById(id)) {
            throw new IllegalArgumentException("Case nao encontrado");
        }
        caseRepository.deleteById(id);
    }

    private void populate(Case c, CaseRequest request) {
        c.setNomeProjeto(request.getNomeProjeto());
        c.setCliente(request.getCliente());
        c.setDescricao(request.getDescricao());
        c.setImagemCapa(request.getImagemCapa());
        c.setDepoimento(request.getDepoimento());
        c.setTags(new HashSet<>(resolveTags(request.getTagIds())));
        c.setExibirAoPublico(request.isExibirAoPublico());
        applyFeatured(c, request.isFeatured());
    }

    private void applyFeatured(Case c, boolean featured) {
        if (featured) {
            caseRepository.findByFeaturedTrue().ifPresent(current -> {
                if (!current.getId().equals(c.getId())) {
                    current.setFeatured(false);
                    caseRepository.save(current);
                }
            });
        }
        c.setFeatured(featured);
    }

    private List<Tag> resolveTags(List<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return List.of();
        return tagRepository.findAllById(tagIds);
    }
}