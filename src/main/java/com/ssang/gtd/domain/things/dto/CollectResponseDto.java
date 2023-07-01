package com.ssang.gtd.domain.things.dto;

import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.global.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectResponseDto {
        private Long id;
        private String content;
        private BoardType type;
        private LocalDateTime modifiedDate;
        private LocalDateTime createdDate;
        public static CollectResponseDto convertToDto(Collect collect) {
            return new CollectResponseDto(
                    collect.getId(),
                    collect.getContent(),
                    collect.getType(),
                    collect.getModifiedDate(),
                    collect.getCreateDate()
        );
    }
}
