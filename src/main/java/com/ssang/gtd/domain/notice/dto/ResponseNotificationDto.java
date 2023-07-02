package com.ssang.gtd.domain.notice.dto;

import com.ssang.gtd.domain.notice.Notification;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "알림 Dto")
@Getter
@Setter
public class ResponseNotificationDto {
    private Long id;
    private String content;
    private String url;
    private Boolean isRead;
    private String createdAt;

    @Builder
    public ResponseNotificationDto(Notification notification) {
        this.id = notification.getId();
        this.content = notification.getContent();
        this.url = notification.getUrl();
        this.isRead = notification.getIsRead();
        this.createdAt = String.valueOf(notification.getCreateDate());
    }

}