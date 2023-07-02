package com.ssang.gtd.domain.notice.dao;

import com.ssang.gtd.domain.notice.Notification;
import com.ssang.gtd.domain.user.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByReceiver(Member member);
}
