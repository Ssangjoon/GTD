package com.ssang.gtd.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    int uNo;
    String id;
    String password;
    String name;
    String status;
    String email;
    String role;
    String publicKey;
    String privateKey;

}
