package com.ssang.gtd.docs;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnumDocs {

    // 문서화하고 싶은 모든 enum값을 명시
    Map<String,String> gender;
    Map<String,String> memberStatus;
    Map<String,String> role;
    Map<String,String> boardType;


}