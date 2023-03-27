package com.ssang.gtd.user.controller;

import com.ssang.gtd.things.controller.CollectController;
import com.ssang.gtd.user.service.MemberService;
import com.ssang.gtd.user.dto.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
    @Autowired
    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @GetMapping("/member")
    public List<MemberDto> getList(){
        return memberService.list();
    }
    @GetMapping("/member/{id}")
    public MemberDto get(@PathVariable("id") int id){
        return memberService.get(id);
    }
    @PostMapping("/member")
    public int post(@RequestBody MemberDto dto){
        return memberService.post(dto);
    }
    @PutMapping("/member")
    public int update(@RequestBody MemberDto dto){
        return memberService.put(dto);
    }
    @DeleteMapping("/member/{id}")
    public int delete(@PathVariable("id") int id){
        return memberService.delete(id);
    }
}
