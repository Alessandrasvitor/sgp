package com.sansyro.sgpspring.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.Builder;

@Getter
@Setter
@Builder
public class MessageError {
    private String userMessage;
    private Integer code;
}
