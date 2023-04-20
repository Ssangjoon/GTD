package com.ssang.gtd.user.controller;

import com.ssang.gtd.user.domain.Member;
import com.ssang.gtd.user.dto.MemberCreateDto.MemberCreateData;
import com.ssang.gtd.user.dto.MemberCreateDto.MemberCreateRequest;
import com.ssang.gtd.user.dto.MemberCreateDto.MemberCreateResponse;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.dto.MemberUpdateDto.MemberUpdateData;
import com.ssang.gtd.user.dto.MemberUpdateDto.MemberUpdateRequest;
import com.ssang.gtd.user.dto.MemberUpdateDto.MemberUpdateResponse;
import com.ssang.gtd.user.service.MemberService;
import com.ssang.gtd.utils.crypto.SHACrypto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/joinUp")
    public MemberCreateResponse joinUp(@RequestBody MemberCreateRequest dto) throws Exception {
        Member member = memberService.post(dto);
        return new MemberCreateResponse(MemberCreateData.create(member));
    }

    @PutMapping("/member")
    public MemberUpdateResponse update(@RequestBody MemberUpdateRequest dto)throws Exception{
        Member member = memberService.put(dto);
        return new MemberUpdateResponse(MemberUpdateData.update(member));
    }
    @DeleteMapping("/member/")
    public int delete(@RequestBody MemberDto dto){
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));
        return memberService.delete(dto);
    }
    @GetMapping("/member")
    public List<MemberDto> getList(){
        return memberService.list();
    }

    @GetMapping("/member/{id}")
    public MemberDto get(@PathVariable("id") int id){
        return memberService.get(id);
    }
}
