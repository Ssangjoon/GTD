package com.ssang.gtd.things;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CollectController {
    private static final Logger logger = LogManager.getLogger(CollectController.class);

    private final CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @GetMapping("/test")
    public String hello(String name){
        return collectService.test();
    }

    @GetMapping("/collection")
    public List<CollectionDto> collections(){
        return collectService.list();
    }
}
