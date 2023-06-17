package com.ssang.gtd.domain.user.dao;

import com.ssang.gtd.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
