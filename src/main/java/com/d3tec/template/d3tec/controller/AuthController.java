package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.config.security.UsuarioPrincipal;
import com.d3tec.template.d3tec.dto.LoginRequest;
import com.d3tec.template.d3tec.dto.LoginResponse;
import com.d3tec.template.d3tec.dto.ForgotPasswordRequest;
import com.d3tec.template.d3tec.dto.GenericMessageResponse;
import com.d3tec.template.d3tec.dto.RefreshRequest;
import com.d3tec.template.d3tec.dto.RegisterRequest;
import com.d3tec.template.d3tec.dto.RegisterResponse;
import com.d3tec.template.d3tec.dto.ResendVerificationRequest;
import com.d3tec.template.d3tec.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticacao", description = "Endpoints de autenticacao e cadastro")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais invalidas",
                    content = @Content),
            @ApiResponse(responseCode = "429", description = "Rate limit excedido",
                    content = @Content)
    })
    @SecurityRequirement(name = "")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro", description = "Cadastra um usuario com role basica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario cadastrado com sucesso", content = @Content),
            @ApiResponse(responseCode = "500", description = "Role nao encontrada", content = @Content),
            @ApiResponse(responseCode = "409", description = "E-mail ja cadastrado", content = @Content),
            @ApiResponse(responseCode = "429", description = "Rate limit excedido", content = @Content)
    })
    @SecurityRequirement(name = "")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(authService.register(registerRequest));
    }

    @GetMapping("/verify-email")
    @Operation(summary = "Confirmar email", description = "Confirma o email do usuario a partir de um token enviado por email.")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok(authService.verifyEmail(token));
    }

    @PostMapping("/resend-verification")
    @Operation(summary = "Reenviar email de confirmacao", description = "Reenvia o email de confirmacao para usuarios ainda nao verificados.")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> resendVerification(@RequestBody @Valid ResendVerificationRequest request) {
        return ResponseEntity.ok(authService.resendVerification(request));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar recuperacao de senha", description = "Envia instrucoes por email para continuar o fluxo de recuperacao.")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> forgotPassword(@RequestBody @Valid ForgotPasswordRequest request) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "Deslogue do sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(@ApiResponse(responseCode = "204", description = "No content"))
    public ResponseEntity<?> logout(@AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal, @RequestBody @Valid RefreshRequest request) {
        authService.logout(usuarioPrincipal.getUserDto().getId(), request);
        return ResponseEntity.noContent().build();
    }
}
