package com.d3tec.template.d3tec.repository;

import com.d3tec.template.d3tec.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    List<Member> findAllByOrderByCreatedAtDesc();
    List<Member> findByExibirAoPublicoTrueOrderByCreatedAtDesc();
}
