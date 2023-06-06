package com.ssang.gtd.user.dao;

import com.ssang.gtd.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
