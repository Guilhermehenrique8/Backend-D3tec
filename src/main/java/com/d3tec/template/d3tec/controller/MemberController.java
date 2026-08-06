package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.entity.Member;
import com.d3tec.template.d3tec.repository.MemberRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @GetMapping("/members")
    @Operation(summary = "Listar membros publicos")
    @SecurityRequirement(name = "")
    public ResponseEntity<List<Member>> listPublic() {
        return ResponseEntity.ok(memberRepository.findByExibirAoPublicoTrueOrderByCreatedAtDesc());
    }
}
