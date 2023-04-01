package de.vantrex.springpaste.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum DeleteAction {

    NOT_FOUND(HttpStatus.NOT_FOUND),
    DELETED(HttpStatus.OK),
    ;

    private final HttpStatus httpStatus;

}
