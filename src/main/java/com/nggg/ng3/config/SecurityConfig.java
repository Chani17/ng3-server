package com.nggg.ng3.config;

import com.nggg.ng3.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;


import java.io.IOException;
import java.util.Map;
import java.util.Date;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${jwt.secretKey}")
    String secretKey;

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/", "/login**", "/oauth2/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler((request, response, authentication) -> {
                            DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authentication.getPrincipal();
                            String email =oAuth2User.getAttribute("email");

                            // DB에 사용자 정보 확인 후 저장 로직 추가
                            userService.addUser(email);

                            String jwtToken = generateToken(oAuth2User.getAttributes());

                            // 응답에 토큰 추가
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("{\"token\": \"" + jwtToken + "\"}");

                            // 클라이언트로 리다이렉트
                            response.sendRedirect("http://localhost:3000/main?token=" + jwtToken);
                        })
                )
                .csrf().disable(); // CSRF 보호를 비활성화 (필요시 조정)
        return http.build();
    }

    private String generateToken(Map<String, Object> attributes) {
        return Jwts.builder()
                .setSubject((String) attributes.get("email"))
                .setExpiration(new Date(System.currentTimeMillis() + 864_000_000)) // 10일간 유효
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }
}