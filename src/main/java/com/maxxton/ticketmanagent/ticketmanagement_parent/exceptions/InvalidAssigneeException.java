package com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions;

@SuppressWarnings("serial")
public class InvalidAssigneeException extends RuntimeException{

    public InvalidAssigneeException(String message) {
        super(message);
    }

    public InvalidAssigneeException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidAssigneeException(Throwable cause) {
        super(cause);
    }
    
}
