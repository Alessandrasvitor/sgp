package com.sansyro.sgpspring.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MessageError {
    private String userMessage;
    private Integer code;
}
