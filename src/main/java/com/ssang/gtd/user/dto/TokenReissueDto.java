package com.ssang.gtd.user.dto;

import lombok.Getter;

import static com.ssang.gtd.jwt.JwtConstants.AT_HEADER;
import static com.ssang.gtd.jwt.JwtConstants.RT_HEADER;

@Getter
public class TokenReissueDto {
    String accessToken;
    String refreshToken;
    public static TokenReissueDto toResponseToken(String token, String type){
        TokenReissueDto dto = new TokenReissueDto();
        if(type.equals(AT_HEADER)){
            dto.accessToken = token;
        } else if (type.equals(RT_HEADER)){
            dto.refreshToken = token;
        }
        return dto;
    }
}
