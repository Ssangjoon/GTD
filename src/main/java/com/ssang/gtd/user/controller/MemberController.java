package com.ssang.gtd.user.controller;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.user.dto.member.MemberCreateDto.MemberCreateData;
import com.ssang.gtd.user.dto.member.MemberCreateDto.MemberCreateRequest;
import com.ssang.gtd.user.dto.member.MemberCreateDto.MemberCreateResponse;
import com.ssang.gtd.user.dto.member.MemberUpdateDto.MemberUpdateData;
import com.ssang.gtd.user.dto.member.MemberUpdateDto.MemberUpdateRequest;
import com.ssang.gtd.user.dto.member.MemberUpdateDto.MemberUpdateResponse;
import com.ssang.gtd.user.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberService memberService;

    @PostMapping("/joinUp")
    public MemberCreateResponse joinUp(@RequestBody MemberCreateRequest dto) throws Exception {
        Member member = memberService.post(dto.toServiceDto());
        return new MemberCreateResponse(MemberCreateData.create(member));
    }

    @PutMapping("/member")
    public MemberUpdateResponse update(@RequestBody MemberUpdateRequest dto)throws Exception{
        Member member = memberService.put(dto.toServiceDto());
        return new MemberUpdateResponse(MemberUpdateData.update(member));
    }
    @DeleteMapping("/member/{id}")
    public void delete(@PathVariable Long id){
        memberService.delete(id);
    }
    @GetMapping("/member")
    public List<Member> getList(){
        return memberService.list();
    }

    @GetMapping("/member/{id}")
    public Optional<Member> get(@PathVariable("id") Long id){
        return memberService.get(id);
    }
}
