package com.ssang.gtd.things.dto.matcol;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.MatCol;
import com.ssang.gtd.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

public class MatColCreateDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class MatColCreateRequest{
        String goal;
        String content;
        Date goalDt;
        Member member;
        Collect collect;
        public MatCol toEntity(){
            return MatCol.builder()
                    .goal(goal)
                    .goalDt(goalDt)
                    .member(member)
                    .content(content)
                    .collect(collect)
                    .build();
        }

    }
}
