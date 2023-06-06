package com.ssang.gtd.docs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface DocumentLinkGenerator {

    static String generateLinkCode(DocUrl docUrl) {
        return String.format("link:common/%s.html[%s %s,role=\"popup\"]", docUrl.pageId, docUrl.text, "코드");
    }

    static String generateText(DocUrl docUrl) {
        return String.format("%s %s", docUrl.text, "코드명");
    }

    @RequiredArgsConstructor
    enum DocUrl {
        MEMBER_STATUS("member-status", "상태"),
        MEMBER_SEX("gender","성별"),
        BOARD_TYPE("board-type","게시판 유형"),
        ROLE("role","고객 권한 상태")
        ;

        private final String pageId;
        @Getter
        private final String text;
    }
}