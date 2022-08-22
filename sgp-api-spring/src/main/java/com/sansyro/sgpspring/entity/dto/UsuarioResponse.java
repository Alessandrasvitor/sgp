package com.sansyro.sgpspring.entity.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioResponse {

    private Long id;
    private String nome;
    private String email;

}
