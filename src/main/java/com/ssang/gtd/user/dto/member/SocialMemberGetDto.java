package com.ssang.gtd.user.dto.member;

import com.ssang.gtd.entity.SocialMember;
import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.oauth2.SocialType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class SocialMemberGetDto {
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SocialMembertGetResponse{
        private SocialMemberGetDto.SocialMembertGetData data;
    }

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SocialMembertGetData{
        private String email;
        private String nickname;
        private String imageurl;
        private int age;
        private String city;
        private Role role;
        private SocialType socialType;
        public static List<SocialMemberGetDto.SocialMembertGetData> toList(List<SocialMember> memberList){
            return memberList.stream()
                    .map(SocialMemberGetDto.SocialMembertGetData::convertToDto)
                    .collect(Collectors.toList());
        }
        public static SocialMemberGetDto.SocialMembertGetData convertToDto(SocialMember socialMember) {
            return new SocialMemberGetDto.SocialMembertGetData(
                    socialMember.getEmail(),
                    socialMember.getNickname(),
                    socialMember.getImageUrl(),
                    socialMember.getAge(),
                    socialMember.getCity(),
                    socialMember.getRole(),
                    socialMember.getSocialType()
            );
        }

    }
}
