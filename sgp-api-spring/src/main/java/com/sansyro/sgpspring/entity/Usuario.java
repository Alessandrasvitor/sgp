package com.sansyro.sgpspring.entity;

import com.sansyro.sgpspring.entity.dto.UsuarioResponse;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="usuario")
public class Usuario {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @NotNull
    private String nome;
    @NotNull
    private String email;
    @NotNull
    //senhas padrão bh1234
    private String senha;

    public UsuarioResponse mapperDTP() {
        return UsuarioResponse.builder().id(id).nome(nome).email(email).build();
    }

}
