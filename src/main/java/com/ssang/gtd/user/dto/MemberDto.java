package com.ssang.gtd.user.dto;

import com.ssang.gtd.oauth2.Role;
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
