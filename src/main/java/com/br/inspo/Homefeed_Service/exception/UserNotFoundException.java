package com.br.inspo.Homefeed_Service.exception;

/**
 * Exception that gets thrown if a user was not found in database.
 */
public class UserNotFoundException extends Exception {

    public UserNotFoundException(String message) {
            super(message);
    }

}
