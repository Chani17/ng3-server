package com.nggg.ng3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/login", "/oauth2/**").permitAll()
                                .anyRequest().authenticated()
                )//루트 경로(/), 로그인 페이지(/login), 그리고 /oauth2/**로 시작하는 모든 경로에 대해서는 인증 없이 접근을 허용
                .oauth2Login(oauth2 ->
                        oauth2.defaultSuccessUrl("/loginSuccess")//OAuth2 인증이 성공하면 사용자를 /loginSuccess 경로로 리디렉션
                                .failureUrl("/loginFailure")
                );
        return http.build();//현재 HttpSecurity 객체에 설정된 모든 보안 설정을 적용한 SecurityFilterChain을 생성하여 반환
    }
}
