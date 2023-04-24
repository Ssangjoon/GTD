package com.ssang.gtd.things.dto.collect;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class CollectionUpdateDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CollectUpdateRequest{
        private Long id;
        private String content;
        private Member member;
        private String type;
        public Collect toEntity() {
            return Collect.builder()
                    .id(id)
                    .content(content)
                    .member(member)
                    .type(type)
                    .build();
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectUpdateResponse{
        private CollectionUpdateDto.CollectUpdateData data;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectUpdateData{
        Long id;
        String content;
        String type;
        Member member;

        public static CollectUpdateData update(Collect collect) {
            return new CollectUpdateData(collect.getId(),collect.getContent(),collect.getType(),collect.getMember());
        }
    }


}
