package com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions;

@SuppressWarnings("serial")
public class ConstraintViolationException extends RuntimeException{

    public ConstraintViolationException(String message) {
        super();
    }

    public ConstraintViolationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConstraintViolationException(Throwable cause) {
        super(cause);
    }
}