package com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions;

@SuppressWarnings("serial")
public class PasswordMismatchEception extends RuntimeException{

    public PasswordMismatchEception(String message) {
        super(message);
    }

    public PasswordMismatchEception(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordMismatchEception(Throwable cause) {
        super(cause);
    }
}

