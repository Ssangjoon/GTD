package com.ssang.gtd.things;

import org.springframework.stereotype.Service;

@Service
public class GettingServiceImpl implements GettingService{
    @Override
    public String test() {
        return "hey";
    }
}
