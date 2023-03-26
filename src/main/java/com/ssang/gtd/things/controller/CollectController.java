package com.ssang.gtd.things.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ssang.gtd.things.dto.CollectionDto;
import com.ssang.gtd.things.dto.MatColDto;
import com.ssang.gtd.things.service.CollectService;
import com.ssang.gtd.things.service.MatCollectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class CollectController {
    private static final Logger logger = LoggerFactory.getLogger(CollectController.class);

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
    public int post(@RequestPart(value = "cDto") CollectionDto cDto,@RequestPart(value = "mDto") MatColDto mDto, List<MultipartFile> files) throws JsonProcessingException {
        try {
            return matCollectService.post(cDto,mDto,files);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

   /* @PostMapping("/material")
    public int post(@RequestBody MatColDto dto){
        return matCollectService.post(dto);
    }*/
}
