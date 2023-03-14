package com.ssang.gtd.things;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GettingController {

    private final  GettingService gettingService;

    public GettingController(GettingService gettingService) {
        this.gettingService = gettingService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String hello(String name){
        return gettingService.test();
    }
}
