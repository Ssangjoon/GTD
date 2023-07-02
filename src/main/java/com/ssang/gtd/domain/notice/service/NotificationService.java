package com.ssang.gtd.domain.notice.service;

import com.ssang.gtd.domain.notice.Notification;
import com.ssang.gtd.domain.notice.dao.EmitterRepository;
import com.ssang.gtd.domain.notice.dao.EmitterRepositoryImpl;
import com.ssang.gtd.domain.notice.dao.NotificationRepository;
import com.ssang.gtd.domain.notice.dto.ResponseNotificationDto;
import com.ssang.gtd.domain.user.dao.MemberSocialTypeRepository;
import com.ssang.gtd.domain.user.domain.Member;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.enums.NotificationType;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberSocialTypeRepository memberSocialTypeRepository;
    private final EmitterRepository emitterRepository = new EmitterRepositoryImpl();
    private final NotificationRepository notificationRepository;

    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60; // 연결시간 1시간

    public SseEmitter sseConnection(Authentication authentication, String lastEventId, HttpServletResponse response) throws Exception {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        MemberSocial member = memberSocialTypeRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new Exception("존재하지 않는 회원"));

        // 시간을 emitterId에 붙여두면 데이터가 유실된 시점을 알 수 있으므로
        // 저장된 Key값 비교를 통해 유실된 데이터만 재전송 할 수 있다
        String emitterId = member.getId() + "_" + System.currentTimeMillis();

        // 클라이언트의 sse 연결 요청에 응답하기 위한 SseEmitter 객체 생성
        // (시간이 지나면 자동으로 클라이언트에서 재연결 요청 보냄)
        // 향후 이벤트가 발생했을 때 클라이언트에게 데이터를 전송할 수 있다.
        SseEmitter emitter = emitterRepository.save(emitterId, new SseEmitter(DEFAULT_TIMEOUT));

        // SseEmitter 의 완료/시간초과/에러로 인한 전송 불가 시 sseEmitter 삭제
        emitter.onCompletion(() -> emitterRepository.deleteById(emitterId));
        emitter.onTimeout(() -> emitterRepository.deleteById(emitterId));
        emitter.onError((e) -> emitterRepository.deleteById(emitterId));

        // Sse 연결이 이뤄진 후,
        // 데이터가 하나도 전송되지 않았는데 SseEmitter의 유효시간이 끝나면 503 에러가 발생.
        // 에러 방지 위한 더미데이터 전송
        sendToClient(emitter, emitterId, "EventStream Created. [memberId=" + member.getId() + "]");

        // lastEventId값이 있는 경우, 저장된 데이터 캐시에서 유실된 데이터들을 다시 전송합니다.
        if (!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByMemberId(String.valueOf(member.getId()));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }
    // Notification을 전송하기 위한 메서드
    public void send(Member receiver, NotificationType notificationType, String content, String url) {

        Notification notification = notificationRepository.save(Notification.builder()
                .receiver(receiver)
                .notificationType(notificationType)
                .content(content)
                .url(url)
                .build());
        String memberId = String.valueOf(receiver.getId());

        // Notification 객체를 만들고 해당 Member의 emitter를 다 불러온다.
        // (여러 브라우저에서 접속할 수 있기 때문에 emitter가 여러개 일 수 있음.)
        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByMemberId(memberId);
        sseEmitters.forEach(
                (key, emitter) -> {
                    // 해당 데이터를 EventCache에 저장 (유실된 데이터 처리 위함)
                    emitterRepository.saveEventCache(key, notification);
                    // 데이터 전송
                    sendToClient(emitter, key, ResponseNotificationDto.builder().notification(notification).build());
                }
        );
    }

    private void sendToClient(SseEmitter emitter, String emitterId, Object data) {
        try {
            //emitter, emitterId와 함께 알림 내용을 클라이언트에 보낸다.
            emitter.send(SseEmitter.event()
                    .id(emitterId)
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(emitterId);
            logger.error("SSE 연결 오류 발생");
        }
    }
    @Transactional
    public void approveCollaboRequest(Long collaborequestid, Member member) {
////        /.../
//
//        //요청한 사람한테 승인 완료 알림 - 게시글 상세 조회로 이동
//        Long postId = post.getId();
//        Member collaboMember = memberRepository.findByNickname(collaboRequest.getNickname())
//                .orElseThrow(() -> new NotFoundException(COLLABO_REQUEST, SERVICE, MEMBER_NOT_FOUND));
//        String url = "/api/post/"+postId;
//        String content = post.getTitle()+"에 대한 콜라보 요청이 승인되었습니다.";
//        notificationService.send(collaboMember, NotificationType.COLLABO_APPROVED, content, url);
    }
}
