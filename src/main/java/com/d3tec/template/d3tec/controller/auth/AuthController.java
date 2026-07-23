package com.d3tec.template.d3tec.controller.auth;

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
@Tag(name = "AutenticaÃ§Ã£o", description = "Endpoints de autenticaÃ§Ã£o e cadastro")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Login realizado com sucesso",
                    content = @Content(schema = @Schema(implementation = LoginResponse.class))),
            @ApiResponse(responseCode = "401", description = "Credenciais invÃ¡lidas",
                    content = @Content),
            @ApiResponse(responseCode = "429", description = "Rate limit excedido",
                    content = @Content)
    })
    @SecurityRequirement(name = "")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest loginRequest) {
        return ResponseEntity.ok(
                authService.login(loginRequest)
        );
    }

    @PostMapping("/register")
    @Operation(summary = "Cadastro", description = "Cadastra um usuÃ¡rio com role bÃ¡sica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "UsuÃ¡rio cadastrado com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Role nÃ£o encontrada",
                    content = @Content),
            @ApiResponse(responseCode = "409", description = "E-mail jÃ¡ cadastrado",
                    content = @Content),
            @ApiResponse(responseCode = "429", description = "Rate limit excedido",
                    content = @Content)
    })
    @SecurityRequirement(name = "")
    public ResponseEntity<RegisterResponse> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return ResponseEntity.ok(
                authService.register(registerRequest)
        );
    }

    @GetMapping("/verify-email")
    @Operation(summary = "Confirmar email", description = "Confirma o email do usuÃ¡rio a partir de um token enviado por email.")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> verifyEmail(@RequestParam String token) {
        return ResponseEntity.ok(authService.verifyEmail(token));
    }

    @PostMapping("/resend-verification")
    @Operation(summary = "Reenviar email de confirmaÃ§Ã£o", description = "Reenvia o email de confirmaÃ§Ã£o para usuÃ¡rios ainda nÃ£o verificados.")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> resendVerification(
            @RequestBody @Valid ResendVerificationRequest request
    ) {
        return ResponseEntity.ok(authService.resendVerification(request));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Solicitar recuperaÃ§Ã£o de senha", description = "Envia instruÃ§Ãµes por email para continuar o fluxo de recuperaÃ§Ã£o.")
    @SecurityRequirement(name = "")
    public ResponseEntity<GenericMessageResponse> forgotPassword(
            @RequestBody @Valid ForgotPasswordRequest request
    ) {
        return ResponseEntity.ok(authService.forgotPassword(request));
    }

    @PostMapping("/logout")
    @Operation(
            summary = "Logout",
            description = "Deslogue do sistema.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponses(@ApiResponse(responseCode = "204", description = "No content"))
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal,
            @RequestBody @Valid RefreshRequest request
    ) {
        authService.logout(usuarioPrincipal.getUserDto().getId(), request);
        return ResponseEntity.noContent().build();
    }
}
