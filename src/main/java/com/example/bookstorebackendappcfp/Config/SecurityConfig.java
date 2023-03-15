package com.example.bookstorebackendappcfp.Config;


import com.example.bookstorebackendappcfp.filter.JwtFilter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Autowired
    private  JwtFilter jwtFilter;

    @Autowired
    private AuthenticationProvider authenticationProvider;

    @Autowired
    private AuthEntryPoint unauthorizedHandler;
     //defines  url authorization permitsand a dds filters
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors();
        http.csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().authorizeHttpRequests()
                .requestMatchers("/api/auth/**").permitAll()
                .and().authorizeHttpRequests()
                .requestMatchers("/api/book/**").permitAll()
                .and().authorizeHttpRequests()
                .anyRequest().authenticated()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .permitAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter,  UsernamePasswordAuthenticationFilter.class);
        return http.build();

    }


}