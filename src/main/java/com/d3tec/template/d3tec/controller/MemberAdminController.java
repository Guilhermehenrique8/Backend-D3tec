package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.config.security.UsuarioPrincipal;
import com.d3tec.template.d3tec.dto.MemberRequest;
import com.d3tec.template.d3tec.entity.Member;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.repository.UserRepository;
import com.d3tec.template.d3tec.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class MemberAdminController {

    private final MemberService memberService;
    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Listar todos os membros")
    public ResponseEntity<List<Member>> listAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um membro pelo id")
    public ResponseEntity<Member> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo membro (usuario + member)")
    public ResponseEntity<Member> create(@RequestBody @Valid MemberRequest request) {
        return ResponseEntity.ok(memberService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar um membro existente")
    public ResponseEntity<Member> update(
            @PathVariable Long id,
            @RequestBody @Valid MemberRequest request,
            @AuthenticationPrincipal UsuarioPrincipal principal
    ) {
        User currentUser = userRepository.findById(principal.getUserDto().getId())
                .orElseThrow();
        Member member = memberService.findById(id);
        if (!memberService.canModify(member, currentUser)) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um membro")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal UsuarioPrincipal principal
    ) {
        User currentUser = userRepository.findById(principal.getUserDto().getId())
                .orElseThrow();
        Member member = memberService.findById(id);
        if (!memberService.canModify(member, currentUser)) {
            return ResponseEntity.status(403).build();
        }
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

