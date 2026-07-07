package com.br.inspo.Homefeed_Service.exception;

/**
 * Exception that gets thrown if a module is not available for a user language.
 */
public class LanguageNotFoundException extends Exception {

    public LanguageNotFoundException(String message) {
        super(message);
    }

}
