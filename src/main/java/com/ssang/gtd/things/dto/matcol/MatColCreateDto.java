package com.ssang.gtd.things.dto.matcol;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.MatCol;
import com.ssang.gtd.entity.MemberSocial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class MatColCreateDto {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MatColCreateRequest{
        private String goal;
        private String content;
        private LocalDate goalDt;
        private Collect collect;
        private MemberSocial member;
        private List<String> steps;

        public MatColServiceDto toServiceDto(){
            MatColServiceDto serviceDto = new MatColServiceDto();
            serviceDto.setContent(this.content);
            serviceDto.setGoal(this.goal);
            serviceDto.setGoalDt(this.goalDt);
            serviceDto.setCollect(this.collect);
            serviceDto.setMember(this.member);
            serviceDto.setSteps(this.steps);
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
        private LocalDate goalDt;
        private Collect collect;
        private MemberSocial member;
        private List<String> steps;
        public static MatColServiceDto initMatColCreateRequest(MatColServiceDto dto, Collect collect){
            MatColServiceDto matColServiceDto = new MatColServiceDto();
            matColServiceDto.goal = dto.goal;
            matColServiceDto.content = dto.content;
            matColServiceDto.goalDt = dto.goalDt;
            matColServiceDto.member = dto.member;
            matColServiceDto.collect = collect;
            matColServiceDto.steps = dto.steps;
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
                    .steps(steps)
                    .build();
        }
    }
}
