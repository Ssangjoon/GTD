package com.ssang.gtd.domain.user.dto.member;

import com.ssang.gtd.global.enums.Role;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    String password;
    String userName;
    String name;
    String status;
    String email;
    Role role;

    /*https://testGTD.com*/
}
