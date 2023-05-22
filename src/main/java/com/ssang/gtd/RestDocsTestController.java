package com.ssang.gtd;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestDocsTestController {

    @GetMapping("/restDocsTest")
    public String restDocsTestAPI() {
        return "test!!";
    }
}