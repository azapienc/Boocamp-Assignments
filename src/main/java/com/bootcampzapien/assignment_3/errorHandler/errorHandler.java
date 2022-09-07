package com.bootcampzapien.assignment_3.errorHandler;

import com.bootcampzapien.assignment_3.exception.BootcampExperienceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class errorHandler {

    @Autowired
    private Environment environment;

    @ExceptionHandler(BootcampExperienceException.class)
    public ResponseEntity<ErrorInfo> experienceExceptionHandler(BootcampExperienceException ex) {
        ErrorInfo error = new ErrorInfo(ex.getMessage(), 001);
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorInfo> invalidFields(Exception ex) {
        log.error(ex.getMessage(), ex);
        String errorMessage;

        WebExchangeBindException exception1 = (WebExchangeBindException) ex;
        errorMessage = exception1.getBindingResult().getAllErrors()
                .stream().map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ErrorInfo error = new ErrorInfo();
        error.setErrorMessage(errorMessage);
        error.setErrorCode(HttpStatus.BAD_REQUEST.value());
        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorInfo> exceptionHandler(Exception ex) {
        log.error(ex.getMessage(), ex);
        ErrorInfo error = new ErrorInfo(environment.getProperty("General.EXCEPTION_MESSAGE"), 1);
        return ResponseEntity.badRequest().body(error);
    }

}
