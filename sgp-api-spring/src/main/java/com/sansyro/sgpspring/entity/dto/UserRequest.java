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
    private String startView;
    private String password;
    private Set<FunctionalityEnum> functionalities;

    public User mapperEntity() {
        return User.builder().name(name).startView(startView).email(email).password(password).functionalities(functionalities).build();
    }
}
