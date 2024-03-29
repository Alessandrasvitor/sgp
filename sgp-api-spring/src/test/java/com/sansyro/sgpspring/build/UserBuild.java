package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.entity.User;
import java.util.HashSet;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class UserBuild {

    public static User getBuild() {
        return User.builder()
            .id(RandomUtils.nextLong(0,10))
            .email(RandomStringUtils.randomAlphabetic(8)+"@"+RandomStringUtils.randomAlphabetic(4)+".com")
            .name(RandomStringUtils.randomAlphabetic(15))
            .password(RandomStringUtils.randomAlphabetic(8))
            .userHashCode(RandomStringUtils.randomAlphabetic(8))
            .checkerCode(RandomStringUtils.randomAlphabetic(10).toUpperCase())
            .functionalities(new HashSet<>())
            .build();
    }


}
