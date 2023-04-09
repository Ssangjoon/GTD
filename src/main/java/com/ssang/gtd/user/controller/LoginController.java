package com.ssang.gtd.user.controller;

import com.ssang.gtd.utils.crypto.RSACrypto;
import com.ssang.gtd.utils.crypto.SHACrypto;
import com.ssang.gtd.user.service.MemberService;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.anno.Login;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;


@RestController
public class LoginController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final MemberService memberService;
    private final RSACrypto rsaCrypto;
    private final SHACrypto shaCrypto;

    public LoginController(MemberService memberService, SHACrypto crypto, RSACrypto rsaCrypto, SHACrypto shaCrypto) {
        this.memberService = memberService;
        this.rsaCrypto = rsaCrypto;
        this.shaCrypto = shaCrypto;
    }


    @PostMapping("login")
    public int login(@RequestBody MemberDto dto, BindingResult bindingResult, HttpServletResponse response, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return 0;
        }
        dto.setPassword(shaCrypto.encryptToHex(dto.getPassword(),"SHA-256"));

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
        HashMap<String, String> rsaKeyPair = rsaCrypto.createKeypairAsString();
        String publicKey = rsaKeyPair.get("publicKey");
        String privateKey = rsaKeyPair.get("privateKey");
        logger.info("만들어진 공개키:" + publicKey);
        logger.info("만들어진 개인키:" + privateKey);

        String plainText = "플레인 텍스트";
        logger.info("평문: " + plainText);

        String encryptedText = rsaCrypto.encrypt(plainText, publicKey);
        logger.info("암호화: " + encryptedText);

        String decryptedText = rsaCrypto.decrypt(encryptedText, privateKey);
        logger.info("복호화: " + decryptedText);
        return 1;
    }


}
