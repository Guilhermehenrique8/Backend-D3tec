package com.d3tec.template.d3tec.service;

import com.d3tec.template.d3tec.dto.MemberRequest;
import com.d3tec.template.d3tec.entity.Member;
import com.d3tec.template.d3tec.entity.Role;
import com.d3tec.template.d3tec.entity.User;
import com.d3tec.template.d3tec.exception.exceptions.ConflictException;
import com.d3tec.template.d3tec.exception.exceptions.NotFoundException;
import com.d3tec.template.d3tec.repository.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public List<Member> findAll() {
        return memberRepository.findAllByOrderByCreatedAtDesc();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Membro nao encontrado"));
    }

    public Member create(MemberRequest request) {
        if (request.getPassword() == null || request.getPassword().isBlank())
            throw new IllegalArgumentException("Senha obrigatoria");
        if (request.getPassword().length() < 6)
            throw new IllegalArgumentException("Senha minima de 6 caracteres");

        String email = request.getEmail().trim().toLowerCase();
        if (userRepository.findByEmail(email).isPresent())
            throw new ConflictException("Ja existe um membro com esse e-mail");

        Role role = resolveRole(request.getRole());
        User user = new User();
        user.setNome(request.getNome());
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(role));
        user.setMfaEnabled(false);
        user.setEmailVerified(true);
        user.setCreatedAt(LocalDateTime.now());
        user = userRepository.save(user);

        Member member = new Member();
        member.setUsuario(user);
        populate(member, request);
        member.setCreatedAt(LocalDateTime.now());
        return memberRepository.save(member);
    }

    public Member update(Long id, MemberRequest request) {
        Member member = findById(id);
        User user = member.getUsuario();

        if (request.getNome() != null) user.setNome(request.getNome());
        if (request.getEmail() != null) user.setEmail(request.getEmail().trim().toLowerCase());
        if (request.getRole() != null) user.setRoles(Set.of(resolveRole(request.getRole())));
        if (request.getPassword() != null && !request.getPassword().isBlank())
            user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        user.setUpdatedAt(LocalDateTime.now());
        userRepository.save(user);

        populate(member, request);
        member.setUpdatedAt(LocalDateTime.now());
        return memberRepository.save(member);
    }

    public void delete(Long id) {
        Member member = findById(id);
        memberRepository.delete(member);
        userRepository.delete(member.getUsuario());
    }

    public boolean canModify(Member target, User currentUser) {
        if (currentUser.getRoles().stream().anyMatch(r -> r.getName().equals("ADMIN"))) return true;
        return target.getUsuario().getRoles().stream().noneMatch(r -> r.getName().equals("ADMIN"));
    }

    private void populate(Member member, MemberRequest r) {
        if (r.getCargo() != null) member.setCargo(r.getCargo());
        if (r.getInstagram() != null) member.setInstagram(r.getInstagram());
        if (r.getGithub() != null) member.setGithub(r.getGithub());
        if (r.getLinkedin() != null) member.setLinkedin(r.getLinkedin());
        if (r.getFotoPerfil() != null) member.setFotoPerfil(r.getFotoPerfil());
        member.setExibirAoPublico(r.isExibirAoPublico());
    }

    private Role resolveRole(String roleName) {
        return roleRepository.findByName(roleName.trim().toUpperCase())
                .orElseThrow(() -> new NotFoundException("Role nao encontrada: " + roleName));
    }
}
