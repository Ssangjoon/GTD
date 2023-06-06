package com.ssang.gtd.user.dto.member;

import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.utils.enums.Role;
import com.ssang.gtd.utils.enums.Gender;
import com.ssang.gtd.utils.enums.MemberStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberServiceDto {
    private String name;
    private String userName;
    private String password;
    private String email;
    private Gender gender;
    private Role role;
    private MemberStatus status;

    public MemberSocial toMemberEntity(){
        MemberSocial memberSocial = new MemberSocial(email, password, userName,name,gender,role,status);
        return memberSocial;
    }
}
