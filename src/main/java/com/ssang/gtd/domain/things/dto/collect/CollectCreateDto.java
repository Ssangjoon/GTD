package com.ssang.gtd.domain.things.dto.collect;

import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.utils.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssang.gtd.domain.things.dto.collect.MemberResponseDto.convertToDto;


public class CollectCreateDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectCreateRequest{
        private String content;
        private MemberSocial member;

        public CollectServiceDto toServiceDto(){
            CollectServiceDto serviceDto = new CollectServiceDto();
            serviceDto.setContent(this.content);
            serviceDto.setMember(this.member);
            return serviceDto;
        }

    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectCreateResponse {
    private CollectCreateDto.CollectCreateData data;
}

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectCreateData {
        private Long id;
        private String content;
        private BoardType type;
        private MemberResponseDto member;

        public static CollectCreateData create(Collect collect) {

            return new CollectCreateData(collect.getId(),collect.getContent(),collect.getType(), convertToDto(collect.getMember()));
        }

    }
}
