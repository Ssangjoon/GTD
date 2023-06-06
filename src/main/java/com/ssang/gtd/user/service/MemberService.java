package com.ssang.gtd.user.service;

import com.ssang.gtd.entity.MemberDetail;
import com.ssang.gtd.entity.MemberSocial;
import com.ssang.gtd.user.dto.member.MemberServiceDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface MemberService {
    public List<MemberSocial> list();
    public Optional<MemberSocial> get(Long id);
    public MemberDetail post(MemberServiceDto dto) throws Exception;
    public MemberSocial put(MemberServiceDto dto) throws Exception;
    public void delete(Long id);
    public ResponseEntity<HttpStatus> logout(HttpServletRequest request);
}
