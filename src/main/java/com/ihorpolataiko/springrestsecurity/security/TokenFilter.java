package com.ihorpolataiko.springrestsecurity.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws IOException, ServletException {

        String authHeaderValue = httpServletRequest.getHeader("Authorization");

        TokenAuthentication tokenAuthentication = new TokenAuthentication(authHeaderValue);
        if (authHeaderValue != null) {
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

}
