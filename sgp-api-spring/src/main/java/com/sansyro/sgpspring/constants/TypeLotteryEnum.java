package com.sansyro.sgpspring.constants;

import lombok.Getter;

@Getter
public enum TypeLotteryEnum {
    MEGA_SENA("Mega-Sena", 6, 60),
    LOTOFACIL("Lotofácil", 6, 60),
    QUINA("Quina", 6, 60),
    LOTOMANIA("Lotomania", 6, 60),
    DUPLA_SENA("Dupla Sena", 6, 60),
    LOTECA("Loteca", 6, 60);

    private String value;
    private int quantity;
    private int max;

    TypeLotteryEnum(String value, int quantity, int max){
        this.value = value;
        this.quantity = quantity;
        this.max = max;
    }

}
