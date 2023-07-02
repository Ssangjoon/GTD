package com.ssang.gtd.domain.notice.dao;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class EmitterRepositoryImpl implements EmitterRepository {
    // 멀티스레드 환경하에서 HashMap을 안전하게 사용하기위해 java에서는 concurrent 패키지를 제공.
    // ConcurrentHashmap을 사용하면 thread-safe가 보장됨.
    // 그냥 사용한다면 ConcurrentModificationException이 발생할 수 있다.
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String, Object> eventCache = new ConcurrentHashMap<>();

    @Override
    public SseEmitter save(String emitterId, SseEmitter sseEmitter) {
        emitters.put(emitterId,sseEmitter);
        return sseEmitter;
    }

    @Override
    public void saveEventCache(String emitterId, Object event) {
        eventCache.put(emitterId,event);
    }

    @Override
    public Map<String, SseEmitter> findAllEmitterStartWithByMemberId(String memberId) {
        return emitters.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Object> findAllEventCacheStartWithByMemberId(String memberId) {
        return eventCache.entrySet().stream()
                .filter(entry -> entry.getKey().startsWith(memberId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void deleteById(String emitterId) {
        emitters.remove(emitterId);
    }

//    @Override
//    public void deleteAllEmitterStartWithId(String memberId) {
//        emitters.forEach(
//                (key,emitter) -> {
//                    if(key.startsWith(memberId)){
//                        emitters.remove(key);
//                    }
//                }
//        );
//    }
//
//    @Override
//    public void deleteAllEventCacheStartWithId(String memberId) {
//        eventCache.forEach(
//                (key,emitter) -> {
//                    if(key.startsWith(memberId)){
//                        eventCache.remove(key);
//                    }
//                }
//        );
//
//    }
}
