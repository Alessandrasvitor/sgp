package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.entity.Course;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

import static java.util.Objects.isNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CourseDTO {

    private Long id;
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

    public static CourseDTO mapper(Course course) {
        if(isNull(course)) return null;
        return CourseDTO.builder()
                .id(course.getId())
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
