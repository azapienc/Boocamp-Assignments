package com.bootcampzapien.assignment2.errorHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.io.IOException;

@Slf4j
public class ErrorHandler {

    @ExceptionHandler(ClassCastException.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(ClassCastException ex) {
        log.error(ex.getMessage(), ex);
        ErrorInfo error = new ErrorInfo("General exception", 1);
        return ResponseEntity.badRequest().body(error);
    }
}
