package com.test.users.exceptions;

public class DuplicateException extends RuntimeException{

        private static final long serialVersionUID = -6331797308186042140L;

        public DuplicateException(String message) {
            super(message);
        }

}
