package com.ssang.gtd.user.dto;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.utils.cons.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberUpdateDto {

    @Getter
    @Setter
    @NoArgsConstructor
    public static class MemberUpdateRequest{
        private String userName;
        private String name;
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
    public static class MemberUpdateResponse {
        private MemberUpdateData data;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberUpdateData {
        private String name;
        private String password;
        private UserRoleEnum role;

        public static MemberUpdateData update(Member member) {
            return new MemberUpdateData(member.getName(), member.getPassword(), member.getRole());
        }
    }
}
