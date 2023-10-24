package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.entity.dto.UserDTO;
import org.apache.commons.lang3.RandomStringUtils;

public class UserDTOBuild {

    public static UserDTO getBuild() {
        return UserDTO.builder()
            .email(RandomStringUtils.randomAlphabetic(8))
            .name(RandomStringUtils.randomAlphabetic(15))
            .startView(RandomStringUtils.randomAlphabetic(8))
            .build();
    }


}
