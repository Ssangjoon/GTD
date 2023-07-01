package com.ssang.gtd.domain.user.dto.member;

import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.enums.Role;
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

        public static MemberUpdateData update(MemberSocial member) {
            return new MemberUpdateData(member.getUserName(), member.getName(), member.getEmail(), member.getRole());
        }
    }
}
