package com.ssang.gtd.docs;

import com.ssang.gtd.global.enums.Role;
import com.ssang.gtd.global.enums.EnumType;
import com.ssang.gtd.global.enums.MemberStatus;
import com.ssang.gtd.global.enums.Gender;
import com.ssang.gtd.global.enums.BoardType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/test")
public class CommonDocController {

    @PostMapping("/error")
    public void errorSample(@RequestBody @Valid SampleRequest dto) {

    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class SampleRequest {

        @NotEmpty
        private String name;

        @Email
        private String email;
    }


    @GetMapping("/enums")
    public ApiResponseDto<EnumDocs> findEnums() {

        // 문서화 하고 싶은 -> EnumDocs 클래스에 담긴 모든 Enum 값 생성
        Map<String, String> memberStatus = getDocs(MemberStatus.values());
        Map<String, String> sex = getDocs(Gender.values());
        Map<String, String> role = getDocs(Role.values());
        Map<String, String> boardType = getDocs(BoardType.values());

        // 전부 담아서 반환 -> 테스트에서는 이걸 꺼내 해석하여 조각을 만들면 된다.
        return ApiResponseDto.of(EnumDocs.builder()
                .memberStatus(memberStatus)
                .gender(sex)
                .role(role)
                .boardType(boardType)
                .build()
        );

    }

    private Map<String, String> getDocs(EnumType[] enumTypes) {
        return Arrays.stream(enumTypes)
                .collect(Collectors.toMap(EnumType::getName, EnumType::getDescription));
    }

}