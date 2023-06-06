package com.ssang.gtd.user.dto.member;

import com.ssang.gtd.entity.MemberDetail;
import com.ssang.gtd.utils.enums.Role;
import com.ssang.gtd.utils.enums.Gender;
import com.ssang.gtd.utils.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class MemberCreateDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MemberCreateRequest{
        private String name;
        private String userName;
        private String password;
        private String email;
        private Role role;
        private Gender gender;


        public MemberServiceDto toServiceDto(){
            MemberServiceDto serviceDto = new MemberServiceDto();
            serviceDto.setName(this.name);
            serviceDto.setUserName(this.userName);
            serviceDto.setPassword(this.password);
            serviceDto.setEmail(this.email);
            serviceDto.setGender(this.gender);
            serviceDto.setRole(Role.USER);
            serviceDto.setStatus(MemberStatus.NORMAL);
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
        private Role role;

        public static MemberCreateData create(MemberDetail member) {
            return new MemberCreateData(member.getName(), member.getRole());
        }
    }
}
