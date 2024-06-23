package com.cooperativa.exception.handler;

import com.cooperativa.exception.IllegalArgumentException;
import com.cooperativa.exception.StandardError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;

@ControllerAdvice
public class IllegalArgumentExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> resourceNotFound(IllegalArgumentException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError();
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("IllegalArgumentException");
        err.setMessage(e.getMessage());
        err.setPath(request.getRequestURI());
        return ResponseEntity.status(status).body(err);
    }
}
