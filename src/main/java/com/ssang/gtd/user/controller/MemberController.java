package com.ssang.gtd.user.controller;

import com.ssang.gtd.utils.crypto.SHACrypto;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/member")
    public int post(@RequestBody MemberDto dto){
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));
        return memberService.post(dto);
    }

    @PutMapping("/member")
    public int update(@RequestBody MemberDto dto){
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));
        return memberService.put(dto);
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
