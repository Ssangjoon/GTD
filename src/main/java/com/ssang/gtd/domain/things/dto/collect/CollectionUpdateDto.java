package com.ssang.gtd.domain.things.dto.collect;

import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.enums.BoardType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static com.ssang.gtd.domain.things.dto.collect.MemberResponseDto.convertToDto;

public class CollectionUpdateDto {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectUpdateRequest{
        private Long id;
        private String content;
        private MemberSocial member;
        private BoardType type;

        public CollectServiceDto toServiceDto(){
            CollectServiceDto serviceDto = new CollectServiceDto();
            serviceDto.setId(this.id);
            serviceDto.setContent(this.content);
            serviceDto.setType(this.type);
            serviceDto.setMember(this.member);
            return serviceDto;
        }
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectUpdateResponse{
        private CollectionUpdateDto.CollectUpdateData data;
    }
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CollectUpdateData{
        private Long id;
        private String content;
        private BoardType type;
        private MemberResponseDto member;

        public static CollectUpdateData update(Collect collect) {

            return new CollectUpdateData(collect.getId(),collect.getContent(),collect.getType(),convertToDto(collect.getMember()));
        }

    }


}
