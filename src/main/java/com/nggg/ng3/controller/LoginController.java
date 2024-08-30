package com.nggg.ng3.controller;

import com.nggg.ng3.service.UserService;
import com.nggg.ng3.service.WearingService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final SecretKey secretKey;

    private final UserService userService;

    private final WearingService wearingService;

    // 생성자에서 secretKey 초기화
    public LoginController(@Value("${jwt.secretKey}") String secretKeyString, UserService userService, WearingService wearingService) {
        this.secretKey = Keys.hmacShaKeyFor(secretKeyString.getBytes());
        this.userService = userService;
        this.wearingService = wearingService;
    }

    @GetMapping("/success")
    public void loginSuccess(OAuth2AuthenticationToken authentication, HttpServletResponse response) throws IOException {
        OAuth2User oAuth2User = authentication.getPrincipal();
        String userId = oAuth2User.getAttribute("email");

        boolean userExists = userService.userExists(userId);

        // 사용자 정보가 없으면 새로운 사용자 추가
        if (!userExists) {
            userService.addUser(userId);

            // 기본적으로 사용자가 착용하는 컴포넌트를 저장 (최초 로그인 시)
            wearingService.saveWearing(userId, 1L);
            wearingService.saveWearing(userId, 15L);
            wearingService.saveWearing(userId, 21L);
            wearingService.saveWearing(userId, 28L);
            wearingService.saveWearing(userId, 32L);
        }

        String jwtToken = generateJwtToken(authentication.getPrincipal());
        // 클라이언트의 메인 페이지로 리디렉션하며 JWT를 쿼리 파라미터로 전달
        response.sendRedirect("http://nggg.com:3000/main?token=" + jwtToken);
    }


    private String generateJwtToken(OAuth2User oAuth2User) {
        String userId = oAuth2User.getAttribute("sub");  // Google의 사용자 ID
        String email = oAuth2User.getAttribute("email");
        // 현재 시간으로부터 100일 후를 만료 시간으로 설정
        Date now = new Date();
        Date expirationTime = new Date(now.getTime() + TimeUnit.DAYS.toMillis(100));

        return Jwts.builder()
                .setSubject(userId)  // 사용자 ID를 sub 클레임에 설정
                .claim("email", email)  // 이메일을 별도의 클레임으로 설정
                .setIssuedAt(now)  // 토큰 발급 시간
                .setExpiration(expirationTime)// 토큰 만료 시간
                .signWith(secretKey)
                .compact();
    }
}
