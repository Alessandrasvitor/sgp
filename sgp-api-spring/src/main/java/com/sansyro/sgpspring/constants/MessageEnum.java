package com.sansyro.sgpspring.constants;

import lombok.Getter;

@Getter
public enum MessageEnum {

    // Messages Success
    MSG_USER_REGISTER("Usuário cadastrado com sucesso!", 2001),


    // Messages Error
    MSG_FIELDS_NOT_FILLED("Um ou mais campos obrigatórios não preenchidos!", 4000),
    MSG_USER_DOUBLE("Email {0} já cadastrado!", 4001),
    MSG_EMAIL_INVALID("Email {0} inválido!", 4002),
    MSG_CHECKER_CODE_INVALID("Código de verificação inválido!", 4002),
    MSG_USER_NOT_FOUND("Usuário não encontrado!", 4004),
    MSG_USER_INVALID("Usuário ou senha inválido!", 4005),

    MSG_INSTITUITION_NOT_FOUND("Instituição não encontrada", 4014),

    MSG_COURSE_DOUBLE("Curso já existe na base de dados.", 4021),
    MSG_COURSE_NOT_FOUND("Curso não encontrado", 4024),

    MSG_LOTERY_NOT_FOUND("Loteria não encontrada", 4054),

    MSG_UNABLE_CONVERT_DATE("Não foi possível converter a data: {}", 4070)


    ;


    private String message;
    private int code;

    MessageEnum(String message, int code) {
        this.message = message;
        this.code = code;
    }

}
