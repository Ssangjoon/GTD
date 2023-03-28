package com.ssang.gtd.user.controller;

import com.ssang.gtd.user.service.MemberService;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.anno.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
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
    @PostMapping("login")
    public int login(@RequestBody MemberDto dto, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return 0;
        }

        MemberDto loginMember = memberService.login(dto);
        logger.info(loginMember.getId());


        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return 0;
        }
        //세션 매니저를 통해 세션 생성및 회원정보 보관
        //세션이 있으면 있는 세션 반환, 없으면 신규 세션 생성
        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        session.setMaxInactiveInterval(1800);//1800초

        return 1;
    }
    @GetMapping("/logout")
    public int logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return 1;
    }
    @GetMapping("/getSession")
    public MemberDto test (
            @Login MemberDto loginMember, HttpServletRequest request) {

        if (loginMember == null) {
            return null;
        }
        logger.info(loginMember.getId());
        return loginMember;
    }
}
