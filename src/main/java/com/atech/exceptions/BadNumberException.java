package com.atech.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadNumberException extends NumberFormatException{

    public BadNumberException() {
        super();
    }

    public BadNumberException(String message) {
        super(message);
    }
}
