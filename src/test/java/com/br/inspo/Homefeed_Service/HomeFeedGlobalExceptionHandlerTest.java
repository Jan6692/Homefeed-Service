package com.br.inspo.Homefeed_Service;

import com.br.inspo.Homefeed_Service.exception.HomeFeedGlobalExceptionHandler;
import com.br.inspo.Homefeed_Service.exception.LanguageNotFoundException;
import com.br.inspo.Homefeed_Service.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HomeFeedGlobalExceptionHandlerTest {

    private final HomeFeedGlobalExceptionHandler handler = new HomeFeedGlobalExceptionHandler();

    /**
     * Test to check correct output for UserNotFoundException.
     */
    @Test
    public void testHandleUserNotFoundException() {
        UserNotFoundException exception = new UserNotFoundException("User with id '5' was not found");

        ResponseEntity<String> response = handler.handleUserNotFoundException(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
        Assertions.assertEquals("User with id '5' was not found", response.getBody());
    }

    /**
     * Test to check correct output for LanguageNotFoundException.
     */
    @Test
    public void testHandleLanguageNotFoundException() {
        LanguageNotFoundException exception = new LanguageNotFoundException("User language 'ru' was not found for module 'Greeting'");

        ResponseEntity<String> response = handler.handleLanguageNotFoundException(exception);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
        Assertions.assertEquals("User language 'ru' was not found for module 'Greeting'", response.getBody());
    }
}
