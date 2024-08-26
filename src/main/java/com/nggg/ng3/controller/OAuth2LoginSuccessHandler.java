package com.nggg.ng3.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OAuth2LoginSuccessHandler {

    @GetMapping("/loginSuccess")
    public String handleLoginSuccess(@AuthenticationPrincipal OAuth2User oauthUser, HttpSession session) {
        // 사용자 정보를 세션에 저장
        session.setAttribute("user", oauthUser);

        // 인증 성공 후 프론트엔드의 메인 페이지로 리디렉션
        return "redirect:http://localhost:3000/main";
    }
}