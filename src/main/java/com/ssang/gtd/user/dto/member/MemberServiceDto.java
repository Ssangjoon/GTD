package com.ssang.gtd.user.dto.member;

import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.test.MemberStatus;
import com.ssang.gtd.test.Gender;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberServiceDto {
    private String name;
    private String userName;
    private String password;
    private String email;
    private Role role;
    private Gender gender;
    private String refreshToken;
    private MemberStatus status;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .userName(userName)
                .password(password)
                .role(role)
                .gender(gender)
                .status(status)
                .email(email).build();
    }
}
