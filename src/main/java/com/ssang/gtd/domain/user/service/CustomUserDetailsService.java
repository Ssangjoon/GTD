package com.ssang.gtd.domain.user.service;

import com.ssang.gtd.domain.user.domain.MemberDetail;
import com.ssang.gtd.domain.user.dao.MemberDetialRepository;
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
    private final MemberDetialRepository memberDetialRepository;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        MemberDetail user = memberDetialRepository.findByEmail(userName)
                .orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다."));
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }

}