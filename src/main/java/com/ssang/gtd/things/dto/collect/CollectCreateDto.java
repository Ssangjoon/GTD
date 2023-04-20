package com.ssang.gtd.things.dto.collect;

import com.ssang.gtd.things.domain.Collect;
import com.ssang.gtd.user.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CollectCreateDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CollectCreateRequest{
        String content;
        Member member;
        public Collect toEntity() {
            return Collect.builder()
                    .content(content)
                    .member(member)
                    .build();
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectCreateResponse {
        private CollectCreateDto.CollectCreateData data;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectCreateData {
        Long id;
        String content;
        String type;
        Member member;

        public static CollectCreateData create(Collect collect) {
            return new CollectCreateData(collect.getId(),collect.getContent(),collect.getType(),collect.getMember());
        }
    }
}