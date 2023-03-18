package com.ssang.gtd.things;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GettingController {
    private static final Logger logger = LogManager.getLogger(GettingController.class);
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
