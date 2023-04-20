package com.ssang.gtd.user.dto;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.utils.cons.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberCreateDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MemberCreateRequest{
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

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberCreateResponse {
        private MemberCreateData data;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberCreateData {
        private String name;
        private String password;
        private UserRoleEnum role;

        public static MemberCreateData create(Member member) {
            return new MemberCreateData(member.getName(), member.getPassword(), member.getRole());
        }
    }
}
