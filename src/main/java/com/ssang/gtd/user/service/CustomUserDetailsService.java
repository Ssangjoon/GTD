package com.ssang.gtd.user.service;

import com.ssang.gtd.entity.Member;
import com.ssang.gtd.user.dao.MemberDao;
import com.ssang.gtd.user.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * UserDetails의 User 객체를 만들어서 반환하는 역할을 수행한다.
 */
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final MemberDao memberDao;
    private final MemberRepository memberRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        Member user = memberRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
//        return User.builder()
//                .email(member.getUserName())
//                .password(member.getPassword())
//                .roles(String.valueOf(member.getRole()))
//                .authorities("USER")
//                .build();
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

}