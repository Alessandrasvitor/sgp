package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.entity.dto.UserRequest;
import org.apache.commons.lang3.RandomStringUtils;

public class UserRequestBuild {

    public static UserRequest getUser() {
        return UserRequest.builder()
                .email(RandomStringUtils.randomAlphabetic(8))
                .name(RandomStringUtils.randomAlphabetic(15))
                .build();
    }


}
