package com.maxxton.ticketmanagent.ticketmanagement_parent.exceptions;

@SuppressWarnings("serial")
public class ItemNotFoundException extends RuntimeException{

    public ItemNotFoundException(String message) {
        super();
    }

    public ItemNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ItemNotFoundException(Throwable cause) {
        super(cause);
    }
}
