package com.sansyro.sgpspring.build;

import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import com.sansyro.sgpspring.entity.Lottery;
import com.sansyro.sgpspring.util.DateUtil;
import java.util.Date;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class LotteryBuild {

    public static Lottery getBuild() {
        return Lottery.builder()
            .id(RandomUtils.nextLong(0, 10))
            .type(TypeLotteryEnum.values()[RandomUtils.nextInt(0, TypeLotteryEnum.values().length)])
            .bet(RandomStringUtils.randomNumeric(16))
            .result(RandomStringUtils.randomNumeric(16))
            .lotteryDate(DateUtil.formatDate(new Date()))
            .resultDate(DateUtil.formatDate(new Date()))
            .paidOut(RandomUtils.nextDouble())
            .won(RandomUtils.nextDouble())
            .build();
    }

}
