package com.ihorpolataiko.springrestsecurity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihorpolataiko.springrestsecurity.transfer.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class ApplicationAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private ObjectMapper objectMapper;

    @Autowired
    public ApplicationAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException {

        ErrorResponse errorResponse =
                new ErrorResponse(request.getRequestURI(), Collections.singletonList("Unauthorized"));

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), errorResponse);

    }

}
