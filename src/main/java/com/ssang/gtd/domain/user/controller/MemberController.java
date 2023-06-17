package com.ssang.gtd.domain.user.controller;

import com.ssang.gtd.domain.user.dao.MemberDetialRepository;
import com.ssang.gtd.domain.user.dao.MemberSocialTypeRepository;
import com.ssang.gtd.domain.user.dto.member.MemberCreateDto;
import com.ssang.gtd.domain.user.dto.member.MemberGetDto;
import com.ssang.gtd.domain.user.dto.member.MemberUpdateDto;
import com.ssang.gtd.domain.user.service.MemberService;
import com.ssang.gtd.domain.user.domain.MemberDetail;
import com.ssang.gtd.domain.user.domain.MemberSocial;
import com.ssang.gtd.domain.user.dao.MemberDao;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberService memberService;
    private final MemberSocialTypeRepository memberSocialTypeRepository;
    private final MemberDetialRepository memberDetialRepository;
    private final MemberDao memberDao;

    @PostMapping("/joinUp")
    public MemberCreateDto.MemberCreateResponse joinUp(@RequestBody MemberCreateDto.MemberCreateRequest dto) throws Exception {
        MemberDetail member = memberService.post(dto.toServiceDto());
        return new MemberCreateDto.MemberCreateResponse(MemberCreateDto.MemberCreateData.create(member));
    }

    @PutMapping("/member")
    public MemberUpdateDto.MemberUpdateResponse update(@RequestBody MemberUpdateDto.MemberUpdateRequest dto)throws Exception{
        MemberSocial member = memberService.put(dto.toServiceDto());
        return new MemberUpdateDto.MemberUpdateResponse(MemberUpdateDto.MemberUpdateData.update(member));
    }

    @DeleteMapping("/member/{id}")
    public void delete(@PathVariable Long id){
        memberService.delete(id);
    }

    @GetMapping("/member")
    public List<MemberGetDto.MembertGetData> getList(){
        return MemberGetDto.MembertGetData.toList(memberService.list());
    }

    @GetMapping("/member/{id}")
    public MemberGetDto.MembertGetResponse get(@PathVariable("id") Long id){
        MemberSocial member = memberService.get(id).get();
        return new MemberGetDto.MembertGetResponse(MemberGetDto.MembertGetData.convertToDto(member));
    }
}
