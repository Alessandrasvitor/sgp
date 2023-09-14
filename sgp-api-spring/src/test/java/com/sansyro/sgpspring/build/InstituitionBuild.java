package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.entity.Instituition;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class InstituitionBuild {

    public static Instituition getBuild() {
        return Instituition.builder()
            .id(RandomUtils.nextLong())
            .name(RandomStringUtils.randomAlphabetic(15))
            .address(RandomStringUtils.randomAlphabetic(8))
            .quantity(RandomUtils.nextFloat())
            .build();
    }

}
