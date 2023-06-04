package com.ssang.gtd;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {
    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }
    @GetMapping("/oauth2/sign-up")
    public String ouath2UserLoginPage(){
        return "login";
    }
}
