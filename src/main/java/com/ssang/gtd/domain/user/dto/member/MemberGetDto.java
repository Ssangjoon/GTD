package com.ssang.gtd.domain.user.dto.member;

import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.global.utils.enums.Role;
import com.ssang.gtd.global.oauth2.SocialType;
import com.ssang.gtd.global.utils.enums.Gender;
import com.ssang.gtd.global.utils.enums.MemberStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class MemberGetDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MembertGetResponse{
        private MemberGetDto.MembertGetData data;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MembertGetData{
        private String userName;
        private String name;
        private String email;
        private Gender gender;
        private Role role;
        private MemberStatus status;
        private LocalDateTime createDate;
        private LocalDateTime modifiedDate;
        private String nickname;
        private String profileImg;
        private SocialType socialType;
        private String socialId; // 로그인한 소셜 타입의 식별자 값 (일반 로그인인 경우 null)
        public static List<MembertGetData> toList(List<MemberSocial> memberList){
            return memberList.stream()
                    .map(MembertGetData::convertToDto)
                    .collect(Collectors.toList());
        }
        public static MembertGetData convertToDto(MemberSocial member) {
            return new MembertGetData(
                    member.getUserName(),
                    member.getName(),
                    member.getEmail(),
                    member.getGender(),
                    member.getRole(),
                    member.getStatus(),
                    member.getCreateDate(),
                    member.getModifiedDate(),
                    member.getNickname(),
                    member.getProfileImg(),
                    member.getSocialType(),
                    member.getSocialId()
            );
        }

    }
}
