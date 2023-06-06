package com.ssang.gtd.utils.enums;

import lombok.Getter;

@Getter
public enum BoardType implements EnumType {
    COLLECTION("collection","해결을 요구하는 모든 일들을 모은다.")
    ,MAT_COLLECTION("matCollection","목표와 구체적 행동을 기재하여 구체화 시킨다.")
    ,IMMEDIATELY("immediately","즉시 실행 및 해결이 가능한 일들.")
    ,PROJECT("project","해결을 위해 여러 단계가 필요한 일들.")
    ,CALENDER("calender","특정한 날짜에만 해결 가능한 일들")
    ,DELEGATE("delegate","내가 할 수 없는 일들")
    ,NEXT_LIST("next_list","가능한 빠른 해결을 요구하는 일들")
    ,MAYBE("maybe","당장 취할 액션이 없거나 지금 할일이 아니거나")
    ,TRASH("trash","어디에도 속하지 않는 목록들")
    ;

    BoardType(String type, String info) {
        this.type = type;
        this.info = info;
    }

    private String type;
    private String info;

    @Override
    public String getDescription() {
        return this.info;
    }

    @Override
    public String getName() {
        return this.name();
    }
}