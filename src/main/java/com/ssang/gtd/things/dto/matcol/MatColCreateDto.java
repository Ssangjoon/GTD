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
        public MatColServiceDto toServiceDto(){
            MatColServiceDto serviceDto = new MatColServiceDto();
            serviceDto.setContent(this.content);
            serviceDto.setGoal(this.goal);
            serviceDto.setGoalDt(this.goalDt);
            serviceDto.setMember(this.member);
            serviceDto.setCollect(this.getCollect());
            return serviceDto;
        }
    }
    @Getter
    @Setter
    @NoArgsConstructor
    public static class MatColServiceDto{
        private Long id;
        private String goal;
        private String content;
        private Date goalDt;
        private Member member;
        private Collect collect;
        public static MatColServiceDto initMatColCreateRequest(MatColServiceDto dto, Collect collect){
            MatColServiceDto matColServiceDto = new MatColServiceDto();
            matColServiceDto.goal = dto.goal;
            matColServiceDto.content = dto.content;
            matColServiceDto.goalDt = dto.goalDt;
            matColServiceDto.member = dto.member;
            matColServiceDto.collect = collect;
            return matColServiceDto;
        }
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
    }
}
