package com.ssang.gtd.user.dto;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.utils.cons.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

public class MemberLoginDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MemberLoginRequest{
        private String id;
        private String password;
        private UserRoleEnum role;

        public Member toEntity(){
            return Member.builder()
                    .id(id)
                    .password(password)
                    .role(role)
                    .build();
        }
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberLoginResponse {
        private MemberCreateData data;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberCreateData {
        private String id;
        private String name;
        private OffsetDateTime createdAt;

        public static MemberCreateData create(Member member) {
            return new MemberCreateData(member.getId(), member.getName(), member.getCreatedAt());
        }
    }
}
