package com.ssang.gtd.user.service;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.user.dto.MemberServiceDto;
import com.ssang.gtd.utils.TokenInfoVO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    public List<Member> list();
    public Optional<Member> get(Long id);
    public Member post(MemberServiceDto dto) throws Exception;
    public Member put(MemberServiceDto dto) throws Exception;
    public void delete(Long id);
    public void logout(HttpServletRequest request);
    public ResponseEntity<TokenInfoVO> login(MemberDto dto);
}
