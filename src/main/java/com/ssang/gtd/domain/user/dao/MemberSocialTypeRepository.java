package com.ssang.gtd.domain.user.dao;

import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.oauth2.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberSocialTypeRepository extends JpaRepository<MemberSocial, Long>{
    Optional<MemberSocial> findByEmail(String email);
    boolean existsByUserNameOrEmail(String username, String email);

    Optional<MemberSocial> findByUserName(String uName);

    Optional<MemberSocial> findBySocialTypeAndSocialId(SocialType socialType, String socialId);
}