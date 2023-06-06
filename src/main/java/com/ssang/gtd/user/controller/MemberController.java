package com.ssang.gtd.user.controller;

import com.ssang.gtd.entity.MemberDetail;
import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dao.MemberDetialRepository;
import com.ssang.gtd.user.dao.MemberSocialTypeRepository;
import com.ssang.gtd.user.dto.member.MemberCreateDto.MemberCreateData;
import com.ssang.gtd.user.dto.member.MemberCreateDto.MemberCreateRequest;
import com.ssang.gtd.user.dto.member.MemberCreateDto.MemberCreateResponse;
import com.ssang.gtd.user.dto.member.MemberGetDto;
import com.ssang.gtd.user.dto.member.MemberUpdateDto.MemberUpdateData;
import com.ssang.gtd.user.dto.member.MemberUpdateDto.MemberUpdateRequest;
import com.ssang.gtd.user.dto.member.MemberUpdateDto.MemberUpdateResponse;
import com.ssang.gtd.user.service.MemberService;
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
    public MemberCreateResponse joinUp(@RequestBody MemberCreateRequest dto) throws Exception {
        MemberDetail member = memberService.post(dto.toServiceDto());
        return new MemberCreateResponse(MemberCreateData.create(member));
    }

    @PutMapping("/member")
    public MemberUpdateResponse update(@RequestBody MemberUpdateRequest dto)throws Exception{
        MemberSocial member = memberService.put(dto.toServiceDto());
        return new MemberUpdateResponse(MemberUpdateData.update(member));
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
