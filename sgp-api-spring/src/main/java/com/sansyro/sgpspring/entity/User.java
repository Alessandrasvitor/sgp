package com.sansyro.sgpspring.entity;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.dto.UserResponse;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="user")
public class User {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    //senhas padrão 123456
    private String password;

    @NotNull
    @Column(name="user_hash_code")
    private String userHashCode;
    private String token;

    @ElementCollection(targetClass = FunctionalityEnum.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name="functionality_user", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "functionality")
    private Set<FunctionalityEnum> functionalities;

    public UserResponse mapperDTP() {
        return UserResponse.builder().id(id).name(name).email(email).token(token).functionalities(functionalities).build();
    }

}
