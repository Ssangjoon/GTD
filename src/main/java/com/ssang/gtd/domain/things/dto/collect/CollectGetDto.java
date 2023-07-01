package com.ssang.gtd.domain.things.dto.collect;

import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.things.dto.MemberResponseDto;
import com.ssang.gtd.domain.user.domain.MemberDetail;
import com.ssang.gtd.global.enums.BoardType;
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
            MemberDetail member = collect.getMember();
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
