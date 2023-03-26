package com.ssang.gtd.user.service;

import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dto.MemberDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    private static final Logger logger = LoggerFactory.getLogger(MemberServiceImpl.class);
    private final MemberDao memberDao;

    public MemberServiceImpl(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public List<MemberDto> list() {
        return memberDao.list();
    }

    @Override
    public MemberDto get(int id) {
        return memberDao.get(id);
    }

    @Override
    public int post(MemberDto dto) {
        return memberDao.post(dto);
    }

    @Override
    public int put(MemberDto dto) {
        return memberDao.put(dto);
    }

    @Override
    public int delete(int id) {
        return memberDao.delete(id);
    }
}
