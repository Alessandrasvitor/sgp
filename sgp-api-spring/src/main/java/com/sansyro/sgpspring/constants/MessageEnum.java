package com.sansyro.sgpspring.constants;

import lombok.Getter;

@Getter
public enum MessageEnum {

    // Messages Success
    MSG_USER_REGISTER("Usuário cadastrado com sucesso!", 2001),


    // Messages Error
    MSG_FIELDS_NOT_FILLED("Um ou mais campos obrigatórios não preenchidos!", 4000),
    MSG_USER_DOUBLE("Usuário {0} já cadastrado!", 4001),
    MSG_EMAIL_INVALID("Email {0} inválido!", 4002),
    MSG_CHECKER_CODE_INVALID("Código de verificação inválido!", 4002),
    MSG_USER_NOT_FOUND("Usuário não encontrado!", 4004),
    MSG_USER_INVALID("Usuário ou senha inválido!", 4005);

    private String message;
    private int code;

    MessageEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }

}
