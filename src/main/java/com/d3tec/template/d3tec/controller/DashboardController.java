package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.DashboardStats;
import com.d3tec.template.d3tec.repository.CaseRepository;
import com.d3tec.template.d3tec.repository.PostRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class DashboardController {

    private final PostRepository postRepository;
    private final CaseRepository caseRepository;

    @GetMapping("/admin/dashboard")
    @Operation(summary = "Estatisticas do dashboard")
    public ResponseEntity<DashboardStats> stats() {
        long postsPublicados = postRepository.countByExibirAoPublicoTrue();
        long postsTotal = postRepository.count();
        long casesPublicados = caseRepository.countByExibirAoPublicoTrue();
        long casesTotal = caseRepository.count();

        return ResponseEntity.ok(new DashboardStats(
                postsPublicados,
                postsTotal - postsPublicados,
                casesPublicados,
                casesTotal - casesPublicados
        ));
    }
}
