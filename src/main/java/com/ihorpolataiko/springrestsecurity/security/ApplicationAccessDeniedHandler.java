package com.ihorpolataiko.springrestsecurity.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ihorpolataiko.springrestsecurity.transfer.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

@Component
public class ApplicationAccessDeniedHandler implements AccessDeniedHandler {

    private ObjectMapper objectMapper;

    @Autowired
    public ApplicationAccessDeniedHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException {

        ErrorResponse errorResponse =
                new ErrorResponse(request.getRequestURI(), Collections.singletonList("Forbidden"));

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        objectMapper.writeValue(response.getOutputStream(), errorResponse);

    }

}
