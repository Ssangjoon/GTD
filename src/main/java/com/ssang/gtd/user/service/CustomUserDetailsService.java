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
    public UserDetails loadUserByUsername(String uName) throws UsernameNotFoundException {
        Optional<Member> optionalMemberDto = memberRepository.findByUserName(uName);
        Member member = optionalMemberDto.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        UserDetails result = createUserDetails(member);
        return result;
    }

    // 해당하는 User 의 데이터가 존재한다면 UserDetails 객체로 만들어서 리턴
    private UserDetails createUserDetails(Member member) {
        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .roles(String.valueOf(member.getRole()))
                .authorities("USER")
                .build();
    }
}