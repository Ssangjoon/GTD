package com.ssang.gtd.things.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssang.gtd.entity.Collect;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateData;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateRequest;
import com.ssang.gtd.things.dto.collect.CollectCreateDto.CollectCreateResponse;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto.CollectUpdateData;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto.CollectUpdateRequest;
import com.ssang.gtd.things.dto.collect.CollectionUpdateDto.CollectUpdateResponse;
import com.ssang.gtd.things.dto.matcol.MatColCreateDto.MatColCreateRequest;
import com.ssang.gtd.things.service.CollectService;
import com.ssang.gtd.things.service.MatCollectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class CollectController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectService collectService;
    private final MatCollectService matCollectService;

    @GetMapping("/collection")
    public List<Collect> getList(){ return collectService.list();}
    @GetMapping("/collection/{id}")
    public Optional<Collect> get(@PathVariable("id") Long id){
        return collectService.get(id);
    }
    @PostMapping("/collection")
    public CollectCreateResponse post(@RequestBody CollectCreateRequest dto){
        Collect collect =  collectService.post(dto.toServiceDto());
        return new CollectCreateResponse(CollectCreateData.create(collect));
    }
    @PutMapping("/collection")
    public CollectUpdateResponse update(@RequestBody CollectUpdateRequest dto) throws Exception {
        Collect collect = collectService.put(dto.toServiceDto());
        return new CollectUpdateResponse(CollectUpdateData.update(collect));
    }
    @DeleteMapping("/collection/{id}")
    public void delete(@PathVariable("id") Long id){
        collectService.delete(id);
    }

    @PostMapping("/material")
    public int post(@RequestPart MatColCreateRequest dto, @RequestParam(required = false) List<MultipartFile> files) throws JsonProcessingException {
        try {
            return matCollectService.post(dto.toServiceDto(),files);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
   /* @PostMapping("/material")
    public int post(@RequestBody MatColDto dto){
        return matCollectService.post(dto);
    }*/
}
