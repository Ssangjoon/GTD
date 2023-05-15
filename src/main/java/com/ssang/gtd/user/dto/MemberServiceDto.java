package com.ssang.gtd.user.dto;

import com.ssang.gtd.auth.Role;
import com.ssang.gtd.entity.Member;
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
    private String refreshToken;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .userName(userName)
                .password(password)
                .role(role)
                .email(email).build();
    }
}
