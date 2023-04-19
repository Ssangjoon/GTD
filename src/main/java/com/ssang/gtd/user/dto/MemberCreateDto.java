package com.ssang.gtd.user.dto;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.utils.cons.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberCreateDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MemberCreateRequest{
        private String id;
        private String name;
        private String password;
        private String email;
        private UserRoleEnum role;

        public Member toEntity(){
            return Member.builder()
                    .id(id)
                    .name(name)
                    .password(password)
                    .role(role)
                    .email(email).build();
        }
    }
}
