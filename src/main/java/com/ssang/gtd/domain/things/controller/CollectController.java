package com.ssang.gtd.domain.things.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssang.gtd.domain.things.domain.Collect;
import com.ssang.gtd.domain.things.domain.MatCol;
import com.ssang.gtd.domain.things.dto.collect.CollectCreateDto.CollectCreateData;
import com.ssang.gtd.domain.things.dto.collect.CollectCreateDto.CollectCreateRequest;
import com.ssang.gtd.domain.things.dto.collect.CollectCreateDto.CollectCreateResponse;
import com.ssang.gtd.domain.things.dto.collect.CollectGetDto;
import com.ssang.gtd.domain.things.dto.collect.CollectionUpdateDto.CollectUpdateData;
import com.ssang.gtd.domain.things.dto.collect.CollectionUpdateDto.CollectUpdateRequest;
import com.ssang.gtd.domain.things.dto.collect.CollectionUpdateDto.CollectUpdateResponse;
import com.ssang.gtd.domain.things.dto.matcol.MatColCreateDto.MatColCreateRequest;
import com.ssang.gtd.domain.things.dto.matcol.MatColGetDto;
import com.ssang.gtd.domain.things.service.CollectService;
import com.ssang.gtd.domain.things.service.MatCollectService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CollectController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectService collectService;
    private final MatCollectService matCollectService;

    @GetMapping("/collection")
    public CollectGetDto.CollectGetResponse getList(){
        return new CollectGetDto.CollectGetResponse(CollectGetDto.CollectGetData.toList(collectService.list()));
    }

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
    public MatCol post(@RequestPart(value = "materialCollection")  MatColCreateRequest dto,
                       @RequestPart(value = "files", required = false) List<MultipartFile> files) throws JsonProcessingException {
        try {
            return matCollectService.post(dto.toServiceDto(),files);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @GetMapping("/material/{id}")
    public MatColGetDto.MatColGetResponse getMatCol(@PathVariable("id") Long id){
        return matCollectService.get(id);
    }
    @GetMapping("/material")
    public List<MatColGetDto.MatColGetResponse> getMatColList(){ return matCollectService.list();}
//    @PostMapping("/material/type")
//    public int modifiedType(@RequestBody MatColUpdateDto.MatColUpdateRequest dto) throws JsonProcessingException {
//        return 1;
//    }

   /* @PostMapping("/material")
    public int post(@RequestBody MatColDto dto){
        return matCollectService.post(dto);
    }*/
}
