package com.ssang.gtd.domain.things.dto.matcol;

import com.ssang.gtd.domain.things.domain.FileEntity;
import com.ssang.gtd.global.enums.BoardType;
import com.ssang.gtd.global.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class MatColGetDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatColGetResponse{
        private Long id;
        private String goal;
        private String content;
        private LocalDate goalDt;
//        private CollectResponseDto collect;
        private Long collectId;
        private String collectContent;
        private BoardType type;
        private LocalDateTime modifiedDate;
        private LocalDateTime createdDate;
//        private MemberResponseDto member;
        private Long memberId;
        private String userName;
        private String name;
        private Gender gender;

        private FileEntity file;
        private List<String> steps;
    }

}
