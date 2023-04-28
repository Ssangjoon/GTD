package com.ssang.gtd.user.dto;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.utils.cons.UserRoleEnum;
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
    private UserRoleEnum role;

    public Member toEntity(){
        return Member.builder()
                .name(name)
                .userName(userName)
                .password(password)
                .role(role)
                .email(email).build();
    }
}
