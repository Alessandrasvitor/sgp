package com.sansyro.sgpspring.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


@DiscriminatorValue("bet")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="bet")
public class Bet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private String bet;
    private String result;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "bet_date")
    private Date betDate;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "result_date")
    private Date resultDate;
    @Column(name = "paid_out")
    private Double paidOut;
    private Double lucre;

    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
