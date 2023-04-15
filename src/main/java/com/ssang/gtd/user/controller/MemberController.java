package com.ssang.gtd.user.controller;

import com.ssang.gtd.utils.crypto.RSACrypto;
import com.ssang.gtd.utils.crypto.SHACrypto;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
public class MemberController {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/joinUp")
    public int member(@RequestBody MemberDto dto) throws Exception {
        return memberService.post(dto);
    }
    @PostMapping("/sessionMember")
    public int sessionMember(@RequestBody MemberDto dto){
        logger.info("joinMember 호출됨");
        // 암복호화를 위한 키 생성
        HashMap<String, String> rsaKeyPair = RSACrypto.createKeypairAsString();
        dto.setPublicKey(rsaKeyPair.get("publicKey"));
        dto.setPrivateKey(rsaKeyPair.get("privateKey"));

        // 비밀번호 암호화
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));

        return memberService.sessionPost(dto);
    }

    @PutMapping("/member")
    public int update(@RequestBody MemberDto dto){
        logger.info("member udate 호출됨");
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));
        return memberService.put(dto);
    }
    @DeleteMapping("/member/")
    public int delete(@RequestBody MemberDto dto){
        logger.info("member delete 호출됨");
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));
        return memberService.delete(dto);
    }
    @GetMapping("/member")
    public List<MemberDto> getList(){
        logger.info("member List 호출됨");
        return memberService.list();
    }

    @GetMapping("/member/{id}")
    public MemberDto get(@PathVariable("id") int id){
        logger.info("member get 호출됨");
        return memberService.get(id);
    }
}
