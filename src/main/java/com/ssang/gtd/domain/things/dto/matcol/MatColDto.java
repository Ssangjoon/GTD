package com.ssang.gtd.domain.things.dto.matcol;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatColDto {
    int mcNo;
    int cNo;
    String goal;
    String content;
    Date gDt;
}
