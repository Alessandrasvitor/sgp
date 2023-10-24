package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.Date;

public class CourseDTOBuild {

    public static CourseDTO getBuild() {
        return CourseDTO.builder()
            .name(RandomStringUtils.randomAlphabetic(15))
            .description(RandomStringUtils.randomAlphabetic(8))
            .startDate(new Date())
            .endDate(new Date())
            .idInstituition(RandomUtils.nextLong())
            .notation(Float.valueOf(8))
            .priority(RandomUtils.nextInt())
            .status(StatusEnum.PENDING.name())
            .category(CategoryEnum.INFORMATICA.name())
            .finished(Boolean.FALSE)
            .build();
    }

}
