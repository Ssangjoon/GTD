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
    private final RSACrypto rsaCrypto;

    public MemberController(MemberService memberService, RSACrypto rsaCrypto) {
        this.memberService = memberService;
        this.rsaCrypto = rsaCrypto;
    }

    @PostMapping("/member")
    public int joinMember(@RequestBody MemberDto dto){
        // 암복호화를 위한 키 생성
        HashMap<String, String> rsaKeyPair = rsaCrypto.createKeypairAsString();
        dto.setPublicKey(rsaKeyPair.get("publicKey"));
        dto.setPrivateKey(rsaKeyPair.get("privateKey"));
        dto.setPassword(SHACrypto.encryptToHex(dto.getPassword(),"SHA-256"));
        logger.info(dto.toString());
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
