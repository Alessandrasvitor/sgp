package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.constants.FunctionalityEnum;
import lombok.*;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserResponse {

    private Long id;
    private String name;
    private String startView;
    private String email;
    private Set<FunctionalityEnum> functionalities;

}
