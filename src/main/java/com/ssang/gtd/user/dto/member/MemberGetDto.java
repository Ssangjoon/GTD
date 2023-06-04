package com.ssang.gtd.user.dto.member;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.oauth2.Role;
import com.ssang.gtd.test.Gender;
import com.ssang.gtd.test.MemberStatus;
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
        public static List<MembertGetData> toList(List<Member> memberList){
            return memberList.stream()
                    .map(MembertGetData::convertToDto)
                    .collect(Collectors.toList());
        }
        public static MembertGetData convertToDto(Member member) {
            return new MembertGetData(
                    member.getUserName(),
                    member.getName(),
                    member.getEmail(),
                    member.getGender(),
                    member.getRole(),
                    member.getStatus(),
                    member.getCreateDate(),
                    member.getModifiedDate()
            );
        }

    }
}
