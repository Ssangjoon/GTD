package com.ssang.gtd.user.controller;

import com.ssang.gtd.utils.crypto.SHACrypto;
import com.ssang.gtd.user.service.MemberService;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.anno.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import static com.ssang.gtd.utils.crypto.RSACrypto.*;
import static java.net.URLDecoder.decode;
import static java.net.URLEncoder.encode;

@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private final MemberService memberService;

    public LoginController(MemberService memberService, SHACrypto crypto) {
        this.memberService = memberService;
    }


    @PostMapping("login")
    public int login(@RequestBody MemberDto dto, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return 0;
        }

        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));
        MemberDto loginMember = memberService.login(dto);
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
    @GetMapping("/test")
    public int test(HttpServletResponse response, HttpServletRequest request) throws UnsupportedEncodingException {
        HashMap<String, String> rsaKeyPair = createKeypairAsString();
        String publicKey = rsaKeyPair.get("publicKey");
        String privateKey = rsaKeyPair.get("privateKey");
        System.out.println("만들어진 공개키:" + publicKey);
        System.out.println("만들어진 개인키:" + privateKey);

        String plainText = "플레인 텍스트";
        System.out.println("평문: " + plainText);

        String encryptedText = encrypt(plainText, publicKey);
        System.out.println("암호화: " + encryptedText);

        String decryptedText = decrypt(encryptedText, privateKey);
        System.out.println("복호화: " + decryptedText);
        return 1;
    }


}
