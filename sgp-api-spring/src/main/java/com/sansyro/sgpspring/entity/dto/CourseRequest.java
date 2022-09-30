package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.FunctionalityEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.Course;
import com.sansyro.sgpspring.entity.Instituition;
import com.sansyro.sgpspring.entity.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CourseRequest {

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Float notation;
    private Integer priority;
    private StatusEnum status;
    private CategoryEnum category;
    private Boolean finished;
    private Long idInstituition;
    private Long idUser;

    public Course mapperEntity() {
        return Course.builder().name(name).description(description).startDate(startDate).endDate(endDate)
                .notation(notation).priority(priority).status(status).category(category).build();
    }
}
