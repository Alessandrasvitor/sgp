package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.entity.User;
import lombok.*;

import java.util.Set;

import static java.util.Objects.isNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String startView;
    private String email;
    private FunctionalityEnum init;
    private boolean flActive;
    private Set<FunctionalityEnum> functionalities;

    public static UserDTO mapper(User user) {
        if(isNull(user)) return null;
        return UserDTO.builder()
                .id(user.getId())
                .startView(user.getStartView())
                .name(user.getName())
                .email(user.getEmail())
                .functionalities(user.getFunctionalities())
                .flActive(user.isFlActive())
                .build();
    }

}
