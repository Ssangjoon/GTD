package com.ssang.gtd.user.service;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Map;

public interface AccountService {
    void updateRefreshToken(String username, String refreshToken);
    Map<String, String> refresh(HttpServletRequest request);
}
