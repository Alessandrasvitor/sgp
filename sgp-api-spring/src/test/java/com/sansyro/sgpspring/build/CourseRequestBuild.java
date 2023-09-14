package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.dto.CourseRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

public class CourseRequestBuild {

    public static CourseRequest getBuild() {
        return CourseRequest.builder()
            .name(RandomStringUtils.randomAlphabetic(15))
            .description(RandomStringUtils.randomAlphabetic(8))
            .startDate(new Date())
            .endDate(new Date())
            .idUser(RandomUtils.nextLong())
            .idInstituition(RandomUtils.nextLong())
            .notation(Float.valueOf(8))
            .priority(RandomUtils.nextInt())
            .status(StatusEnum.PENDING)
            .category(CategoryEnum.INFORMATICA)
            .finished(Boolean.FALSE)
            .build();
    }

}
