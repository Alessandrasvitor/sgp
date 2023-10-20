package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.Course;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CourseResponse {

    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Float notation;
    private Integer priority;
    private String status;
    private String category;
    private Boolean finished;
    private Long idInstituition;
    private Long idUser;

    public CourseResponse mapper(Course course) {
        return CourseResponse.builder()
                .name(course.getName())
                .description(course.getDescription())
                .startDate(course.getStartDate())
                .endDate(course.getEndDate())
                .notation(course.getNotation())
                .priority(course.getPriority())
                .status(course.getStatus().name())
                .idInstituition(course.getInstituition().getId())
                .idUser(course.getUser().getId())
                .finished(course.getFinished())
                .category(course.getCategory().name()).build();
    }
}
