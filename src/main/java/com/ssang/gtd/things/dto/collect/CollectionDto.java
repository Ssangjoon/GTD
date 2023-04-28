package com.ssang.gtd.things.dto.collect;

import com.ssang.gtd.things.dto.matcol.MatColDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionDto {
    int cNo;
    int uNo;
    String content;
    String type;
    Date cDt;
    Date uDt;
    String test;

    MatColDto mcDto;
}
