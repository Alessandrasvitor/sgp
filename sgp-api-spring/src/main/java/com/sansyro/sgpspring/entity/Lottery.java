package com.sansyro.sgpspring.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import com.sansyro.sgpspring.entity.dto.LotteryDTO;
import com.sansyro.sgpspring.util.DateUtil;
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
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;

@DiscriminatorValue("lottery")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Getter
@Setter
@Entity
@Table(name="lottery")
public class Lottery implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeLotteryEnum type;
    private String bet;
    private String result;
    @Column(name = "lottery_date")
    private String lotteryDate;
    @Column(name = "result_date")
    private String resultDate;
    @Column(name = "paid_out")
    private Double paidOut;
    private Double won;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public static Lottery mapper(LotteryDTO dto) {
        return Lottery.builder()
                .id(dto.getId())
                .type(TypeLotteryEnum.valueOf(dto.getType()))
                .bet(dto.getBet())
                .lotteryDate(DateUtil.formatDate(dto.getLotteryDateType()))
                .result(dto.getResult())
                .resultDate(DateUtil.formatDate(dto.getResultDateType()))
                .paidOut(dto.getPaidOut())
                .won(dto.getWon())
        .build();
    }

    public Lottery clone() throws CloneNotSupportedException {
        return (Lottery) super.clone();
    }

}
