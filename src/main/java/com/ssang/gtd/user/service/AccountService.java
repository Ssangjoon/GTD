package com.ssang.gtd.user.service;

import java.util.Map;

public interface AccountService {
    void updateRefreshToken(String username, String refreshToken);
    Map<String, String> refresh(String refreshToken);
}
