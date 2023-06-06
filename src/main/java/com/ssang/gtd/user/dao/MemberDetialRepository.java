package com.ssang.gtd.user.dao;

import com.ssang.gtd.entity.MemberDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberDetialRepository extends JpaRepository<MemberDetail, Long> {
    Optional<MemberDetail> findByEmail(String email);
    boolean existsByUserNameOrEmail(String username, String email);

    Optional<MemberDetail> findByUserName(String uName);
}
