package com.sansyro.sgpspring.constants;

import lombok.Getter;

@Getter
public enum TypeBetEnum {
    MEGA_SENA("Mega-Sena"),
    LOTOFACIL("Lotofácil"),
    QUINA("Quina"),
    LOTOMANIA("Lotomania"),
    DUPLA_SENA("Dupla Sena"),
    LOTECA("Loteca");

    private String value;

    TypeBetEnum(String value){
        this.value = value;
    }

}
