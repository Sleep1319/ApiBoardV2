package com.ung.apiboardv2.apiboardv2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.GET,"/sign-up/**"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/sign-up"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/sign-in/**"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/sign-in"),
                                        AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/api/board/new")
                                ).permitAll()
                                .anyRequest().permitAll()//일단 모든 요청에 권한 해제

                );

        return http.build();
    }

    @Bean
    public PasswordEncoder bCryptPasswordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
