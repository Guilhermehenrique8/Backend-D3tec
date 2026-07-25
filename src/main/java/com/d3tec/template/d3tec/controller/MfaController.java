package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.config.security.UsuarioPrincipal;
import com.d3tec.template.d3tec.dto.LoginResponse;
import com.d3tec.template.d3tec.dto.mfa.MfaConfirmRequest;
import com.d3tec.template.d3tec.dto.mfa.MfaDisableRequest;
import com.d3tec.template.d3tec.dto.mfa.MfaSetupResponse;
import com.d3tec.template.d3tec.dto.mfa.MfaVerifyRequest;
import com.d3tec.template.d3tec.service.auth.MfaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mfa")
@RequiredArgsConstructor
@Tag(name = "MFA", description = "Endpoints para setup e validacao de autenticacao em dois fatores (TOTP)")
public class MfaController {

    private final MfaService mfaService;

    @GetMapping("/setup")
    @Operation(
            summary = "Gerar dados de setup do MFA (QR Code)",
            description = "Retorna os dados necessarios para configurar MFA (TOTP) em um aplicativo autenticador (Google Authenticator, Microsoft Authenticator etc). Requer JWT de acesso.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dados do setup retornados com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MfaSetupResponse.class))),
            @ApiResponse(responseCode = "401", description = "Nao autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissao de acesso", content = @Content)
    })
    @PreAuthorize("hasAuthority('PRIV_MFA_SELF_MANAGE')")
    public ResponseEntity<?> mfaSetup(@AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal) {
        return ResponseEntity.ok(mfaService.mfaSetupForUser(usuarioPrincipal.getUserDto().getId()));
    }

    @PostMapping("/confirm")
    @Operation(
            summary = "Confirmar e habilitar MFA",
            description = "Apos escanear o QR Code, o usuario envia o codigo TOTP (6 digitos). Se correto, o MFA e habilitado.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "MFA habilitado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Request invalido", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nao autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissao de acesso", content = @Content),
            @ApiResponse(responseCode = "409", description = "Estado invalido", content = @Content)
    })
    @PreAuthorize("hasAuthority('PRIV_MFA_SELF_MANAGE')")
    public ResponseEntity<?> confirmMfa(@AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal, @Valid @RequestBody MfaConfirmRequest req) {
        mfaService.confirmMfa(usuarioPrincipal.getUserDto().getId(), req.getCode());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/verify")
    @Operation(
            summary = "Verificar MFA no login",
            description = "Usado quando /auth/login retorna mfaRequired=true. Envie mfaToken e mfaCode.",
            security = @SecurityRequirement(name = "")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "MFA verificado, JWT emitido", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "400", description = "Request invalido", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nao autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissao de acesso", content = @Content)
    })
    public ResponseEntity<?> verifyMfa(@Valid @RequestBody MfaVerifyRequest req) {
        return ResponseEntity.ok(mfaService.verifyMfa(req));
    }

    @DeleteMapping
    @Operation(
            summary = "Desabilitar MFA",
            description = "Remove a autenticacao em dois fatores da conta autenticada.",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "MFA removido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Request invalido", content = @Content),
            @ApiResponse(responseCode = "401", description = "Nao autenticado", content = @Content),
            @ApiResponse(responseCode = "403", description = "Sem permissao de acesso", content = @Content)
    })
    @PreAuthorize("hasAuthority('PRIV_MFA_SELF_DISABLE')")
    public ResponseEntity<?> disableMfa(@AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal, @Valid @RequestBody MfaDisableRequest request) {
        mfaService.disableMfa(usuarioPrincipal.getUserDto().getId(), request);
        return ResponseEntity.noContent().build();
    }
}
