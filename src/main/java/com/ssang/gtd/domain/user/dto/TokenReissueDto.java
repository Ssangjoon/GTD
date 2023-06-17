package com.ssang.gtd.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.ssang.gtd.global.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.ssang.gtd.global.jwt.JwtConstants.REFRESH_TOKEN_HEADER;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TokenReissueDto {
    private String accessToken;
    private String refreshToken;


    public TokenReissueDto withToken(String token, String type) {
        return new TokenReissueDto(
                type.equals(ACCESS_TOKEN_HEADER) ? token : this.accessToken,
                type.equals(REFRESH_TOKEN_HEADER) ? token : this.refreshToken
        );
    }
}
