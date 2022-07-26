package com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions;

@SuppressWarnings("serial")
public class InvalidIdException extends RuntimeException{

    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidIdException(Throwable cause) {
        super(cause);
    }
}