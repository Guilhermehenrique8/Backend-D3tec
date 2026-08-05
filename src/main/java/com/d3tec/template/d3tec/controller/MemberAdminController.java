package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.MemberRequest;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/members")
@RequiredArgsConstructor
public class MemberAdminController {

    private final MemberService memberService;

    @GetMapping
    @Operation(summary = "Listar todos os membros")
    public ResponseEntity<List<User>> listAll() {
        return ResponseEntity.ok(memberService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um membro pelo id")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(memberService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo membro")
    public ResponseEntity<User> create(@RequestBody @Valid MemberRequest request) {
        return ResponseEntity.ok(memberService.create(request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Editar um membro existente")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody @Valid MemberRequest request) {
        return ResponseEntity.ok(memberService.update(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir um membro")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        memberService.delete(id);
        return ResponseEntity.noContent().build();
    }
}