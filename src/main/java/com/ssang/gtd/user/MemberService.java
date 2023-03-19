package com.ssang.gtd.user;

import org.springframework.stereotype.Service;

public interface MemberService {
    int joinMember(MemberDto dto);

    int userEdit(MemberDto dto);

    MemberDto joinIn(MemberDto dto);
}
