package com.ihorpolataiko.springrestsecurity.exception;

import com.ihorpolataiko.springrestsecurity.transfer.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ExceptionsInterceptor {

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResponse defaultExceptionHandler(@NonNull HttpServletRequest request, Exception ex) throws Exception {

        if (ex instanceof AccessDeniedException || ex instanceof AuthenticationException) {
            throw ex;
        }

        return new ErrorResponse(request.getRequestURI(), Collections.singletonList(ex.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(IncorrectCredentialsException.class)
    public ErrorResponse incorrectCredentialsExceptionExceptionHandler(@NonNull HttpServletRequest request,
                                                                       @NonNull IncorrectCredentialsException ex) {

        return new ErrorResponse(request.getRequestURI(), Collections.singletonList(ex.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidExceptionHandler(@NonNull HttpServletRequest request,
                                                                @NonNull MethodArgumentNotValidException ex) {

        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();

        List<String> messages = fieldErrors.stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .collect(Collectors.toList());

        return new ErrorResponse(request.getRequestURI(), messages);
    }
}
