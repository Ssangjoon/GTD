package com.ssang.gtd.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {
    int uNo;
    String id;
    String password;
    String name;
    String status;
    String email;
}
