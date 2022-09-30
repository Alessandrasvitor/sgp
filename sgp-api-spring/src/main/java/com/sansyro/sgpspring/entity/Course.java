package com.sansyro.sgpspring.entity;

import com.sansyro.sgpspring.constants.CategoryEnum;
import com.sansyro.sgpspring.constants.StatusEnum;
import com.sansyro.sgpspring.entity.dto.CourseRequest;
import lombok.*;

import javax.persistence.*;
import java.util.Date;


@DiscriminatorValue("course")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="course")
public class Course {

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

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
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

    public void mapperRequest(CourseRequest request) {
        this.toBuilder().id(this.id).category(request.getCategory()).notation(request.getNotation()).endDate(request.getEndDate())
                .status(request.getStatus()).priority(request.getPriority()).startDate(request.getStartDate())
                .description(request.getDescription()).finished(request.getFinished()).name(request.getName()).build();
    }

}
