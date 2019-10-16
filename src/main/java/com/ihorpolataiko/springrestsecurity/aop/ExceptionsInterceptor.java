package com.ihorpolataiko.springrestsecurity.aop;

import com.ihorpolataiko.springrestsecurity.transfer.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class ExceptionsInterceptor {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(Exception.class)
    public ErrorResponse defaultExceptionHandler(@NonNull HttpServletRequest request, @NonNull Exception ex) {
        return new ErrorResponse(request.getRequestURI(), Collections.singletonList(ex.getMessage()));
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse methodArgumentNotValidExceptionHandler(@NonNull HttpServletRequest request,
                                                                @NonNull MethodArgumentNotValidException ex) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<String> messages = new ArrayList<>();
        fieldErrors.forEach(f -> messages.add(f.getField() + ": " + f.getDefaultMessage()));
        return new ErrorResponse(request.getRequestURI(), messages);
    }
}
