package com.ssang.gtd.user.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenInfoDto {
    private String grantType;
    private String accessToken;
    private String refreshToken;
}
