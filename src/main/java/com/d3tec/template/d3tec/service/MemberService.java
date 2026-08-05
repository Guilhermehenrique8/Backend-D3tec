package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.MemberRequest;
import com.d3tec.template.d3tec.entity.Role;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.exception.exceptions.ConflictException;
import com.d3tec.template.d3tec.exception.exceptions.NotFoundException;
import com.d3tec.template.d3tec.repository.RoleRepository;
import com.d3tec.template.d3tec.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membro não encontrado"));
    }

    public User create(MemberRequest request) {
        String normalizedEmail = request.getEmail().trim().toLowerCase();

        if (userRepository.findByEmail(normalizedEmail).isPresent()) {
            throw new ConflictException("Já existe um membro com esse e-mail");
        }

        Role role = resolveRole(request.getRole());

        User user = new User();
        user.setNome(request.getNome());
        user.setEmail(normalizedEmail);
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));
        user.setMfaEnabled(false);
        user.setEmailVerified(true);
        user.setCreatedAt(LocalDateTime.now());

        return userRepository.save(user);
    }

    public User update(Long id, MemberRequest request) {
        User user = findById(id);

        user.setNome(request.getNome());
        user.setEmail(request.getEmail().trim().toLowerCase());
        user.setRoles(Set.of(resolveRole(request.getRole())));

        if (request.getPassword() != null && !request.getPassword().isBlank()) {
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        }

        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public void delete(Long id) {
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("Membro não encontrado");
        }
        userRepository.deleteById(id);
    }

    private Role resolveRole(String roleName) {
        return roleRepository.findByName(roleName.trim().toUpperCase())
                .orElseThrow(() -> new NotFoundException("Role não encontrada: " + roleName));
    }
}