package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.entity.Usuario;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioRequest {

    private String nome;
    private String email;

    public Usuario mapperEntity() {
        return Usuario.builder().nome(nome).email(email).build();
    }
}
