package com.ssang.gtd.domain.things.dto.matcol;

import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.things.domain.FileEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatColFileDto {
    private Long id;
    private String goal;
    private String content;
    private Collect collect;
    private LocalDate goalDt;
    private FileEntity file;
//    private MemberSocial member;
//    private List<String> steps;
}
