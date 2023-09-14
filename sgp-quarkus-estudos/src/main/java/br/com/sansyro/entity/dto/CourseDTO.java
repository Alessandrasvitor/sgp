package br.com.sansyro.entity.dto;

import br.com.sansyro.constaint.CategoryEnum;
import br.com.sansyro.constaint.StatusEnum;
import br.com.sansyro.entity.Course;

import java.util.Date;

public class CourseDTO {

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
