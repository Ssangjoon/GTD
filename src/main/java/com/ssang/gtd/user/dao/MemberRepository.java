package com.ssang.gtd.user.dao;

import com.ssang.gtd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByEmail(String email);

    Optional<Member> findByUserName(String uName);
    boolean existsByUserNameOrEmail(String username, String email);

}
