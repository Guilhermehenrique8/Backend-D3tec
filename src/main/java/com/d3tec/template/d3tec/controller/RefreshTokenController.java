package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.dto.RefreshRequest;
import com.d3tec.template.d3tec.dto.TokenPairDTO;
import com.d3tec.template.d3tec.service.auth.RefreshTokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/refresh")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenService refreshTokenService;

    @PostMapping
    @Operation(summary = "Renovar tokens", description = "Gera um novo par de access token e refresh token")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Novo par de tokens gerado",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = TokenPairDTO.class)))})
    public ResponseEntity<TokenPairDTO> refresh(@RequestBody RefreshRequest request) {
        return ResponseEntity.ok(refreshTokenService.refresh(request.getRefreshToken()));
    }
}
