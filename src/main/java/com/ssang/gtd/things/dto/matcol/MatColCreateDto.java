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
        public static MatColCreateRequest initMatColCreateRequest(MatColCreateRequest dto, Collect collect){
            MatColCreateRequest matColCreateRequest = new MatColCreateRequest();
            matColCreateRequest.goal = dto.goal;
            matColCreateRequest.content = dto.content;
            matColCreateRequest.goalDt = dto.goalDt;
            matColCreateRequest.member = dto.member;
            matColCreateRequest.collect = collect;
            return matColCreateRequest;
        }
    }
}
