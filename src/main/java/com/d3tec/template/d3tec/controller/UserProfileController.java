package com.d3tec.template.d3tec.controller;

import com.d3tec.template.d3tec.config.security.UsuarioPrincipal;
import com.d3tec.template.d3tec.dto.ProfilePictureRequest;
import com.d3tec.template.d3tec.dto.UserMeResponse;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
@Tag(name = "Perfil do usuario", description = "Endpoints do proprio usuario autenticado")
public class UserProfileController {

    private final UserRepository userRepository;

    @GetMapping
    @Operation(summary = "Dados do usuario logado")
    public ResponseEntity<UserMeResponse> me(
            @AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal
    ) {
        User user = userRepository.findById(usuarioPrincipal.getUserDto().getId())
                .orElseThrow(() -> new IllegalStateException("Usuario nao encontrado"));

        return ResponseEntity.ok(new UserMeResponse(
                user.getNome() != null ? user.getNome() : "Admin",
                user.getEmail()
        ));
    }

    @PatchMapping("/profile-picture")
    @Operation(summary = "Atualizar a foto de perfil do usuario logado")
    public ResponseEntity<User> updateProfilePicture(
            @AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal,
            @RequestBody @Valid ProfilePictureRequest request
    ) {
        User user = userRepository.findById(usuarioPrincipal.getUserDto().getId())
                .orElseThrow(() -> new IllegalStateException("Usuario nao encontrado"));

        user.setProfilePictureUrl(request.getProfilePictureUrl());
        return ResponseEntity.ok(userRepository.save(user));
    }
}
