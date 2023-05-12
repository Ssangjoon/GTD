package com.ssang.gtd.user.dto;

import lombok.Getter;

import static com.ssang.gtd.jwt.JwtConstants.ACCESS_TOKEN_HEADER;
import static com.ssang.gtd.jwt.JwtConstants.REFRESH_TOKEN_HEADER;

@Getter
public class TokenReissueDto {
    String accessToken;
    String refreshToken;
    public static TokenReissueDto toResponseToken(String token, String type){
        TokenReissueDto dto = new TokenReissueDto();
        if(type.equals(ACCESS_TOKEN_HEADER)){
            dto.accessToken = token;
        } else if (type.equals(REFRESH_TOKEN_HEADER)){
            dto.refreshToken = token;
        }
        return dto;
    }
}
