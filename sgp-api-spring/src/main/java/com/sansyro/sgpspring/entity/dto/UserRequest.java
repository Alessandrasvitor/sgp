package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.User;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserRequest {

    private String name;
    private String email;
    private String password;
    private FunctionalityEnum init;
    private Set<FunctionalityEnum> functionalities;

    public User mapperEntity() {
        return User.builder().name(name).email(email).password(password).init(init).functionalities(functionalities).build();
    }
}
