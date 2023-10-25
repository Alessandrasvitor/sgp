package com.sansyro.sgpspring.entity.dto;

import com.sansyro.sgpspring.entity.Lottery;
import com.sansyro.sgpspring.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class LotteryDTO {

    private Long id;
    private String type;
    private String bet;
    private String lotteryDate;
    private Date lotteryDateType;
    private String result;
    private String resultDate;
    private Date resultDateType;
    private Double paidOut;
    private Double won;

    public static LotteryDTO mapper(Lottery lottery) {
        return LotteryDTO.builder()
                .id(lottery.getId())
                .type(lottery.getType().name())
                .bet(lottery.getBet())
                .lotteryDate(lottery.getLotteryDate())
                .lotteryDateType(DateUtil.convertToDate(lottery.getLotteryDate()))
                .result(lottery.getResult())
                .resultDate(lottery.getResultDate())
                .resultDateType(DateUtil.convertToDate(lottery.getResultDate()))
                .paidOut(lottery.getPaidOut())
                .won(lottery.getWon())
                .build();
    }

}
