package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.Course;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

public class CourseBuild {

    public static Course getBuild() {
        return Course.builder()
            .id(RandomUtils.nextLong())
            .name(RandomStringUtils.randomAlphabetic(15))
            .description(RandomStringUtils.randomAlphabetic(8))
            .startDate(new Date())
            .endDate(new Date())
            .user(UserBuild.getBuild())
            .notation(RandomUtils.nextFloat())
            .priority(RandomUtils.nextInt())
            .status(StatusEnum.PENDING)
            .category(CategoryEnum.INFORMATICA)
            .finished(Boolean.FALSE)
            .instituition(InstituitionBuild.getBuild())
            .build();
    }

}
