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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatColFileDto {
    private Long id;
    private String goal;
    private String content;
//    private Collect collect;
    private Long collectId;
    private String collectContent;
    private BoardType type;
    private LocalDateTime modifiedDate;
    private LocalDateTime createdDate;
    private LocalDate goalDt;
//    private MemberSocial member;
    private Long memberId;
    private String userName;
    private String name;
    private Gender gender;
    private List<FileEntity> file;
    private List<String> steps;
}
