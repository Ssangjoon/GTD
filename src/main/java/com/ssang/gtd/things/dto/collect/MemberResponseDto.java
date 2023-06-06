package com.ssang.gtd.things.dto.collect;

import com.ssang.gtd.entity.MemberDetail;
import com.ssang.gtd.test.Gender;
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
