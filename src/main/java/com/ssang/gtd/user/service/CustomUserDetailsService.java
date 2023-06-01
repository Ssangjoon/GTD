package com.ssang.gtd.user.service;

import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dao.MemberRepository;
import com.ssang.gtd.entity.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberDao memberDao;
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Optional<Member> optionalMemberDto = memberRepository.findByUserName(userName);
        Member member = optionalMemberDto.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        return User.builder()
                .username(member.getUserName())
                .password(member.getPassword())
                .roles(String.valueOf(member.getRole()))
                .authorities("USER")
                .build();
    }

}