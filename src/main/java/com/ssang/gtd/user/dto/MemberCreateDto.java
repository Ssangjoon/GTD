package com.ssang.gtd.user.dto;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.utils.cons.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

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
        private String id;
        private String name;
        private String password;
        private UserRoleEnum role;
        private OffsetDateTime createdAt;

        public static MemberCreateData create(Member member) {
            return new MemberCreateData(member.getId(), member.getName(), member.getPassword(), member.getRole(), member.getCreatedAt());
        }
    }
}
