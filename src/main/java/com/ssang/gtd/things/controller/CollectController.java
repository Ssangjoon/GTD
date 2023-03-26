package com.ssang.gtd.things.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssang.gtd.things.dto.CollectionDto;
import com.ssang.gtd.things.dto.MatColDto;
import com.ssang.gtd.things.service.CollectService;
import com.ssang.gtd.things.service.MatCollectService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CollectController {
    private static final Logger logger = LogManager.getLogger(CollectController.class);

    private final CollectService collectService;
    private final MatCollectService matCollectService;

    public CollectController(CollectService collectService, MatCollectService matCollectService) {
        this.collectService = collectService;
        this.matCollectService = matCollectService;
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
        return collectService.post(dto);
    }
    @PutMapping("/collection")
    public int update(@RequestBody CollectionDto dto){
        return collectService.put(dto);
    }
    @DeleteMapping("/collection/{id}")
    public int delete(@PathVariable("id") int id){
        return collectService.delete(id);
    }

    @PostMapping("/material")
    public int post(@RequestBody ObjectNode saveObj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        CollectionDto cDto = mapper.treeToValue(saveObj.get("col"),CollectionDto.class);
        MatColDto mDto = mapper.treeToValue(saveObj.get("matCol"),MatColDto.class);
        return matCollectService.post(cDto,mDto);
    }
   /* @PostMapping("/material")
    public int post(@RequestBody MatColDto dto){
        return matCollectService.post(dto);
    }*/
}
