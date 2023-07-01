package com.ssang.gtd.domain.things.dto.collect;

import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.enums.BoardType;
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
