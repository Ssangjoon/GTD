package com.ssang.gtd.things.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssang.gtd.things.domain.Collect;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateResponse;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateData;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateRequest;
import com.ssang.gtd.things.dto.collect.CollectionDto;
import com.ssang.gtd.things.dto.matcol.MatColDto;
import com.ssang.gtd.things.service.CollectService;
import com.ssang.gtd.things.service.MatCollectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CollectController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectService collectService;
    private final MatCollectService matCollectService;

    @GetMapping("/collection")
    public List<CollectionDto> getList(){ return collectService.list();}
    @GetMapping("/collection/{id}")
    public CollectionDto get(@PathVariable("id") int id){
        return collectService.get(id);
    }
    @PostMapping("/collection")
    public CollectCreateResponse post(@RequestBody CollectCreateRequest dto){
        Collect collect =  collectService.post(dto);
        return new CollectCreateResponse(CollectCreateData.create(collect));
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
