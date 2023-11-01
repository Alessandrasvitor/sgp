package com.sansyro.sgpspring.exception;

import com.sansyro.sgpspring.constants.MessageEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class GenericException extends RuntimeException {

    protected MessageError messageError;
    protected HttpStatus statusCode;

    public GenericException(MessageEnum message, HttpStatus statusCode) {
        super(message.getMessage());
        this.messageError = MessageError.builder().userMessage(message.getMessage())
                        .code(message.getCode()).build();
        this.statusCode = statusCode;
    }

    public GenericException(MessageError message, HttpStatus statusCode) {
        super(message.getUserMessage());
        this.messageError = message;
        this.statusCode = statusCode;
    }

}
