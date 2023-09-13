package com.sansyro.sgpspring.entity.dto;

import lombok.*;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class BetResponse {

    private Long id;
    private String type;
    private String bet;
    private Date betDate;
    private String result;
    private Date resultDate;
    private Double paidOut;
    private Double lucre;
    private UserResponse user;

}
