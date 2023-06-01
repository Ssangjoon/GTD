package com.ssang.gtd.jwt;

public class JwtConstants {
    // Expiration Time
    public static final long MINUTE = 1000 * 60;
    public static final long HOUR = 60 * MINUTE;
    public static final long DAY = 24 * HOUR;
    public static final long MONTH = 30 * DAY;

    public static final long ACCESS_TOKEN_EXP_TIME =  10 * MINUTE;
    public static final long REFRESH_TOKEN_EXP_TIME =  7 * DAY;


    // Header
    public static final String ACCESS_TOKEN_HEADER = "access_token";
    public static final String REFRESH_TOKEN_HEADER = "refresh_token";
    public static final String TOKEN_HEADER_PREFIX = "Bearer ";
}
