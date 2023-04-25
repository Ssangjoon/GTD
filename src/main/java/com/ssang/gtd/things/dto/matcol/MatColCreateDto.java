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
        private Long id;
        private String goal;
        private String content;
        private Date goalDt;
        private Member member;
        private Collect collect;
        public MatCol toEntity(){
            return MatCol.builder()
                    .id(id)
                    .goal(goal)
                    .goalDt(goalDt)
                    .member(member)
                    .content(content)
                    .collect(collect)
                    .build();
        }
        public void addCollectType(Collect collect){
            this.collect = collect;
        }
    }
}
