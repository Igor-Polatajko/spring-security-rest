package com.ihorpolataiko.springrestsecurity.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihorpolataiko.springrestsecurity.transfer.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class TokenFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String authHeaderValue = httpServletRequest.getHeader("Authorization");

        TokenAuthentication tokenAuthentication = new TokenAuthentication(authHeaderValue);
        if (authHeaderValue != null) {
            SecurityContextHolder.getContext().setAuthentication(tokenAuthentication);
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex) {
            HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
            ErrorResponse errorResponse = new ErrorResponse(
                    httpServletRequest.getRequestURI(),
                    Collections.singletonList(ex.getMessage()));

            httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
            httpServletResponse.getWriter().write(convertObjectToJson(errorResponse));
        }

    }

    @Override
    public void destroy() {

    }

    private String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
