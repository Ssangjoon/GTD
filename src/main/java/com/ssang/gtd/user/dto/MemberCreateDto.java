package com.ssang.gtd.user.dto;

import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.entity.Member;
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
        private Role role;

        public MemberServiceDto toServiceDto(){
            MemberServiceDto serviceDto = new MemberServiceDto();
            serviceDto.setName(this.name);
            serviceDto.setUserName(this.userName);
            serviceDto.setPassword(this.password);
            serviceDto.setEmail(this.email);
            serviceDto.setRole(this.role);
            return serviceDto;
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
        private Role role;

        public static MemberCreateData create(Member member) {
            return new MemberCreateData(member.getName(), member.getPassword(), member.getRole());
        }
    }
}
