package com.ssang.gtd.domain.notice.controller;

import com.ssang.gtd.domain.notice.service.NotificationService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final NotificationService notificationService;
    @GetMapping(value = "/connect", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter sseConnection(Authentication authentication, HttpServletResponse response,
                                    @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) throws Exception {
        return notificationService.sseConnection(authentication, lastEventId, response);
    }
}
