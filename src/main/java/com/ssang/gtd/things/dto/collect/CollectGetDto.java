package com.ssang.gtd.things.dto.collect;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.Member;
import com.ssang.gtd.things.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class CollectGetDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectGetResponse{
        private List<CollectGetData> data;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectGetData{
        private Long id;
        private String content;
        private BoardType type;
        private LocalDateTime modifiedDate;
        private LocalDateTime createdDate;
        private MemberResponseDto member;
        public static List<CollectGetData> toList(List<Collect> collectList) {
            return collectList.stream()
                    .map(CollectGetData::fromCollect)
                    .collect(Collectors.toList());
        }

        public static CollectGetData fromCollect(Collect collect) {
            Member member = collect.getMember();
            return new CollectGetData(
                    collect.getId(),
                    collect.getContent(),
                    collect.getType(),
                    collect.getModifiedDate(),
                    collect.getCreateDate(),
                    new MemberResponseDto(
                            member.getId(),
                            member.getUserName(),
                            member.getName(),
                            member.getEmail(),
                            member.getGender()
                    )
            );
        }


    }
}
