package com.sansyro.sgpspring.exception;

import com.sansyro.sgpspring.constants.MessageEnum;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ForbiddenException extends GenericException {

    public ForbiddenException(MessageEnum message) {
        super(message, HttpStatus.FORBIDDEN);
    }

}
