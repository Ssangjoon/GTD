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
        private String name;
        private String password;
        private UserRoleEnum role;

        public static MemberUpdateData update(Member member) {
            return new MemberUpdateData(member.getName(), member.getPassword(), member.getRole());
        }
    }
}
