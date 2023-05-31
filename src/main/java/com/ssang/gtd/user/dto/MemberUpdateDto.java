package com.ssang.gtd.user.dto;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.oauth2.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberUpdateDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberUpdateRequest{
        private String userName;
        private String name;
        private String password;
        private String email;
        private Role role;

        private  String refreshToken;

        public MemberServiceDto toServiceDto(){
            MemberServiceDto serviceDto = new MemberServiceDto();
            serviceDto.setName(this.name);
            serviceDto.setUserName(this.userName);
            serviceDto.setPassword(this.password);
            serviceDto.setEmail(this.email);
            serviceDto.setRole(this.role);
            serviceDto.setRefreshToken(this.refreshToken);
            return serviceDto;
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
        private String userName;
        private String name;
        private String email;
        private Role role;

        public static MemberUpdateData update(Member member) {
            return new MemberUpdateData(member.getUserName(), member.getName(), member.getEmail(), member.getRole());
        }
    }
}
