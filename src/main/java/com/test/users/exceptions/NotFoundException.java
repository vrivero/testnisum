package com.test.users.exceptions;

public class NotFoundException extends Exception{

    private static final long serialVersionUID = -8604207973816331140L;

    public NotFoundException(String message) {
        super(message);
    }

}
