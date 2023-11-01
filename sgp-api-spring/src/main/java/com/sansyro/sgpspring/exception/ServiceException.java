package com.sansyro.sgpspring.exception;

import com.sansyro.sgpspring.constants.MessageEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ServiceException extends GenericException {

    public ServiceException(MessageEnum message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public ServiceException(String message, Integer code) {
        super(MessageError.builder().userMessage(message).code(code).build(), HttpStatus.BAD_REQUEST);
    }

    public ServiceException(String message, Integer code, HttpStatus status) {
        super(MessageError.builder().userMessage(message).code(code).build(), status);
    }

    public ServiceException(MessageEnum message, HttpStatus status) {
        super(message, status);
    }

}
