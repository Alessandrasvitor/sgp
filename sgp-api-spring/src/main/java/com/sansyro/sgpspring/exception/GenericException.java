package com.sansyro.sgpspring.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {

    protected MessageError message;

    public ServiceException() {
        super();
    }

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(MessageError message) {
        this.message = message;
    }

}
