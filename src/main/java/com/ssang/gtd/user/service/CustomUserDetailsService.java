package com.ssang.gtd.user.service;

import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dto.MemberDto;
import com.ssang.gtd.utils.cons.UserRoleEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberDao memberDao;

    public CustomUserDetailsService(MemberDao memberDao) {
        this.memberDao = memberDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<MemberDto> optionalMemberDto = memberDao.getById(username);
        MemberDto member = optionalMemberDto.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        member.setRole(UserRoleEnum.USER);
        UserDetails result = createUserDetails(member);
        return result;
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(MemberDto member) {
        return User.builder()
                .username(member.getId())
                .password(member.getPassword())
                .roles(String.valueOf(member.getRole()))
                //.authorities("USER")
                .build();
    }
}