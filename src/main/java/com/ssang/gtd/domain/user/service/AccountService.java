package com.ssang.gtd.domain.user.service;

import com.ssang.gtd.domain.user.dto.TokenReissueDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AccountService {
    void updateRefreshToken(String username, String refreshToken);
    TokenReissueDto refresh(HttpServletRequest request);
}
