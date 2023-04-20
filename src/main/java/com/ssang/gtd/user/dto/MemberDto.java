package com.ssang.gtd.user.dto;

import com.ssang.gtd.utils.cons.UserRoleEnum;
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
    UserRoleEnum role;

    /*https://testGTD.com*/
}
