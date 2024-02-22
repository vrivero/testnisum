package com.test.users.exceptions;

public class UserInactiveException extends Exception{

    private static final long serialVersionUID = -8186042163317973040L;

    public UserInactiveException(String message) {
        super(message);
    }

}
