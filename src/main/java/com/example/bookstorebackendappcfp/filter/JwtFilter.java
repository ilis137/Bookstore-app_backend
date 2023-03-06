package com.example.bookstorebackendappcfp.filter;

import com.example.bookstorebackendappcfp.Services.IUserService;
import com.example.bookstorebackendappcfp.Services.UserService;
import com.example.bookstorebackendappcfp.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

    @Component
    @RequiredArgsConstructor
    @Slf4j
    public class JwtFilter extends OncePerRequestFilter {

        @Autowired
        private  UserDetailsService userDetailsService;
        @Autowired
        private JWTUtil jwtUtil;
        private final RequestMatcher authPaths = new AntPathRequestMatcher("/api/auth/**");
        private final RequestMatcher bookPaths = new AntPathRequestMatcher("/api/book/**");
        
        @Override
        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException,IOException{
                String token = parseJwt(request);
            if (this.authPaths.matches(request)||this.bookPaths.matches(request)) {
                filterChain.doFilter(request, response);
                return;
            }

            String email=jwtUtil.getEmailFromToken(token);
                    if(email!=null && SecurityContextHolder.getContext().getAuthentication() == null){
                        UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
            
  
            filterChain.doFilter(request, response);
        }


        private String parseJwt(HttpServletRequest request) {
            
            String headerAuth = request.getHeader("Authorization");
            log.info(request.toString());
            if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
                return headerAuth.substring(7, headerAuth.length());
            }
            return "";
        }
    }

