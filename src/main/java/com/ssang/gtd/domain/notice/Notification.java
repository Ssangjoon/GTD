package com.ssang.gtd.domain.notice;

import com.ssang.gtd.domain.BaseEntity;
import com.ssang.gtd.domain.user.domain.Member;
import com.ssang.gtd.global.enums.NotificationType;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Entity
@NoArgsConstructor
public class Notification extends BaseEntity {
    // Notification 객체를 생성.
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private NotificationContent content;

    @Embedded
    private RelatedUrl url;

    @Column(nullable = false)
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationType notificationType;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member receiver;

    @Builder
    public Notification(Member receiver, NotificationType notificationType, String content, String url) {
        this.receiver = receiver;
        this.notificationType = notificationType;
        this.content = new NotificationContent(content);
        this.url = new RelatedUrl(url);
        this.isRead = false;
    }

    public String getContent() {
        return content.getContent();
    }

    public String getUrl() {
        return url.getUrl();
    }

    public void read(){
        isRead = true;
    }
}