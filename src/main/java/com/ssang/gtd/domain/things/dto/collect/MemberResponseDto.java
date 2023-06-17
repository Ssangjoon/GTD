package com.ssang.gtd.domain.things.dto.collect;

import com.ssang.gtd.domain.user.domain.MemberDetail;
import com.ssang.gtd.global.utils.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDto {
    private Long id;
    private String userName;
    private String name;
    private String email;
    private Gender gender;
    public static MemberResponseDto convertToDto(MemberDetail member) {
        return new MemberResponseDto(
                member.getId(),
                member.getUserName(),
                member.getName(),
                member.getEmail(),
                member.getGender()
        );
    }
}
