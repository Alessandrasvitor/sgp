package br.com.sansyro.entity;

import br.com.sansyro.constaint.CategoryEnum;
import br.com.sansyro.constaint.StatusEnum;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@DiscriminatorValue("course")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="course")
public class Course extends PanacheEntityBase {

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

    private Float notation;
    private Integer priority;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusEnum status;
    @Enumerated(EnumType.STRING)
    private CategoryEnum category;
    private Boolean finished;
    private Long userId;

    @Override
    public Course clone() throws CloneNotSupportedException {
        return (Course) super.clone();
    }

    public void start(Long id) {
        this.startDate = new Date();
        this.status = StatusEnum.PROGRESS;
        this.persist();
    }

    public void finish(float notation) {
        this.endDate = new Date();
        this.notation = notation;
        if(notation < 7) {
            this.status = StatusEnum.FAIL;
        } else {
            this.status = StatusEnum.FINISH;
        }

        this.persist();
    }



}
