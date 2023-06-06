package com.ssang.gtd.things.dto.collect;

import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.utils.enums.BoardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CollectServiceDto {
    private Long id;
    private String content;
    private BoardType type;
    private MemberSocial member;
    public Collect toEntity() {
        return Collect.builder()
                .content(content)
                .member(member)
                .type(BoardType.COLLECTION)
                .build();
    }
}
