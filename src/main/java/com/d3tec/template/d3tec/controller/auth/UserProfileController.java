package com.d3tec.template.d3tec.controller.auth;

import com.d3tec.template.d3tec.config.security.UsuarioPrincipal;
import com.d3tec.template.d3tec.dto.ProfilePictureRequest;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users/me")
@RequiredArgsConstructor
@Tag(name = "Perfil do usuÃ¡rio", description = "Endpoints do prÃ³prio usuÃ¡rio autenticado")
public class UserProfileController {

    private final UserRepository userRepository;

    @PatchMapping("/profile-picture")
    @Operation(summary = "Atualizar a foto de perfil do usuÃ¡rio logado")
    public ResponseEntity<User> updateProfilePicture(
            @AuthenticationPrincipal UsuarioPrincipal usuarioPrincipal,
            @RequestBody @Valid ProfilePictureRequest request
    ) {
        User user = userRepository.findById(usuarioPrincipal.getUserDto().getId())
                .orElseThrow(() -> new IllegalStateException("UsuÃ¡rio nÃ£o encontrado"));

        user.setProfilePictureUrl(request.getProfilePictureUrl());
        return ResponseEntity.ok(userRepository.save(user));
    }
}
