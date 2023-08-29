package com.meinz.novotelapi.infra;

import com.meinz.novotelapi.api.response.ExceptionResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DefaultExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception exception) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setExceptionName(exception.getClass().getSimpleName());
        exceptionResponse.setMessage(exception.getMessage());

        return ResponseEntity.badRequest().body(exceptionResponse);
    }
}
