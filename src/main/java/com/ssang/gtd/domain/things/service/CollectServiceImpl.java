package com.ssang.gtd.domain.things.service;

import com.ssang.gtd.domain.things.dao.CollectDao;
import com.ssang.gtd.domain.things.dao.CollectRepository;
import com.ssang.gtd.domain.things.dto.collect.CollectServiceDto;
import com.ssang.gtd.domain.things.domain.Collect;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CollectServiceImpl implements CollectService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final CollectDao collectDao;
    private final CollectRepository collectRepository;

    @Override
    public List<Collect> list() { return collectRepository.findAll(); }
    @Override
    public Optional<Collect> get(Long id) {
        return collectRepository.findById(id); }
    @Override
    public Collect post(CollectServiceDto dto) {
        return collectRepository.save(dto.toEntity());
    }
    @Override
    @Transactional
    public Collect put(CollectServiceDto dto) throws Exception {
        Collect collect = collectRepository.findById(dto.getId()).orElseThrow(() -> new Exception("존재하지 않는 글"));
        if(collect.getMember().getId().equals(dto.getMember().getId())){

            if(!StringUtils.hasText(String.valueOf(dto.getType()))){
                dto.setType(collect.getType());
            }

            collect.update(dto.getContent(), dto.getType());

        }else{
            throw new Exception("작성자가 아닙니다.");
        }
        return collect;
    }
    @Override
    public void delete(Long id) {
        collectRepository.deleteById(id);
    }
}
