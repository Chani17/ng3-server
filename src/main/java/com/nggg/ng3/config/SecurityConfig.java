package com.nggg.ng3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/api/**").authenticated()  // /api/** 경로에 대해 인증 요구
                                .anyRequest().permitAll()  // 그 외 모든 요청은 인증 없이 접근 가능
                )
                .oauth2Login(oauth2 -> oauth2
                        .defaultSuccessUrl("/login/success", true)  // OAuth2 로그인 성공 시 리디렉션 설정
                )
                .csrf().disable();  // CSRF 보호 비활성화 (개발 환경에서 주로 사용)

        return http.build();
    }
}