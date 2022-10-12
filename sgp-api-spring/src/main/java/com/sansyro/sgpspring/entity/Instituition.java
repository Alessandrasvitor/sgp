package com.sansyro.sgpspring.entity;

import lombok.*;

import javax.persistence.*;


@DiscriminatorValue("instituition")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="instituition")
public class Instituition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private Float quantity;

}
