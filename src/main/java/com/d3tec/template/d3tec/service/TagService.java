package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.TagRequest;
import com.d3tec.template.d3tec.entity.Tag;
import com.d3tec.template.d3tec.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;

    public Tag create(TagRequest request) {
        if (tagRepository.existsByNome(request.getNome())) {
            throw new IllegalArgumentException("Já existe uma tag com esse nome");
        }
        Tag tag = new Tag();
        tag.setNome(request.getNome());
        return tagRepository.save(tag);
    }

    public Tag update(Long id, TagRequest request) {
        Tag tag = tagRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tag não encontrada"));
        tag.setNome(request.getNome());
        return tagRepository.save(tag);
    }

    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new IllegalArgumentException("Tag não encontrada");
        }
        tagRepository.deleteById(id);
    }
}