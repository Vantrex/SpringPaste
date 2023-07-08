package de.vantrex.springpaste.exception;

import org.springframework.security.core.AuthenticationException;

public class UserCouldNotBeAuthenticatedException extends AuthenticationException {

    public UserCouldNotBeAuthenticatedException(String message) {
        super(message);
    }
}
