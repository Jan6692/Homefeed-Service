package com.br.inspo.Homefeed_Service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class HomeFeedGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException exception) {
        //TODO: additional exception handling can be added here if needed
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body(exception.getMessage());
    }

    @ExceptionHandler(LanguageNotFoundException.class)
    public ResponseEntity<String> handleLanguageNotFoundException(LanguageNotFoundException exception) {
        //TODO: additional exception handling can be added here if needed
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.TEXT_PLAIN).body(exception.getMessage());
    }

}
