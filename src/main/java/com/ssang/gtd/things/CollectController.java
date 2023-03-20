package com.ssang.gtd.things;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.tags.Param;

import java.util.List;
import java.util.Map;

@RestController
public class CollectController {
    private static final Logger logger = LogManager.getLogger(CollectController.class);

    private final CollectService collectService;

    public CollectController(CollectService collectService) {
        this.collectService = collectService;
    }

    @GetMapping("/collection")
    public List<CollectionDto> getList(){
        return collectService.list();
    }
    @GetMapping("/collection/{id}")
    public CollectionDto get(@PathVariable("id") int id){
        return collectService.get(id);
    }
    @PostMapping("/collection")
    public int post(@RequestBody CollectionDto dto){
        return collectService.post(dto.content);
    }
    @PutMapping("/collection")
    public int update(@RequestBody CollectionDto dto){
        return collectService.put(dto);
    }
    @DeleteMapping("/collection/{id}")
    public int delete(@PathVariable("id") int id){
        return collectService.delete(id);
    }
}
