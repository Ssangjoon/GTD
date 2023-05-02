package com.ssang.gtd.things.dto.collect;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollectServiceDto {
    private Long id;
    private String content;
    private String type;
    private Member member;
    public Collect toEntity() {
        return Collect.builder()
                .content(content)
                .member(member)
                .build();
    }
}
