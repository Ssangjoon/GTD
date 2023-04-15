package com.ssang.gtd.user.controller;

import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.service.MemberService;
import com.ssang.gtd.utils.TokenInfoVO;
import com.ssang.gtd.utils.anno.Login;
import com.ssang.gtd.utils.crypto.SHACrypto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MemberService memberService;

    public LoginController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/test")
    public String test(HttpServletRequest request){
        return "success";
    }

    @PostMapping("login")
    public ResponseEntity<TokenInfoVO> login(@RequestBody MemberDto dto, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) {
        //if (bindingResult.hasErrors()) {
        //    return 0;
        //}
        ResponseEntity<TokenInfoVO> tokenInfoVO = memberService.login(dto);
        return tokenInfoVO;
    }
    @PostMapping("sessionLogin")
    public int sessionLogin(@RequestBody MemberDto dto, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) {
        System.out.println("로깅 확인중");
        logger.info(dto.toString());
        if (bindingResult.hasErrors()) {
            return 0;
        }
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));

        MemberDto loginMember = memberService.sessionLogin(dto);
        logger.info(loginMember.getId());
        logger.info(loginMember.getPassword());

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
    @GetMapping("/getSession")
    public MemberDto getSession (
            @Login MemberDto loginMember, HttpServletRequest request) {

        if (loginMember == null) {
            return null;
        }
        logger.info(loginMember.getId());
        return loginMember;
    }
    @GetMapping("/logout")
    public int logout(HttpServletResponse response, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return 1;
    }
}
