package com.sansyro.sgpspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.dto.CourseDTO;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import java.util.Date;
import java.util.Objects;

import static java.util.Objects.isNull;

@DiscriminatorValue("course")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name="course")
public class Course implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "start_date")
    private Date startDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn(name = "instituition_id", referencedColumnName = "id")
    private Instituition instituition;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    private Float notation;
    private Integer priority;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private Boolean finished;

    public static Course mapper(CourseDTO dto) {
        if(isNull(dto)) return null;
        return Course.builder()
                .id(dto.getId())
                .category(CategoryEnum.valueOf(dto.getCategory()))
                .notation(dto.getNotation())
                .endDate(dto.getEndDate())
                .status(StatusEnum.valueOf(dto.getStatus()))
                .priority(dto.getPriority())
                .startDate(dto.getStartDate())
                .description(dto.getDescription())
                .finished(dto.getFinished())
                .name(dto.getName()).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (isNull(o) || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(user.getId(), course.user.getId());
    }

    public Course clone() throws CloneNotSupportedException {
        return (Course) super.clone();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, user);
    }
}
