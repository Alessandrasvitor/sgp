package com.sansyro.sgpspring.unit;

import static com.sansyro.sgpspring.constants.NotificationTypeEnum.EMAIL;
import static com.sansyro.sgpspring.constants.NotificationTypeEnum.FACEBOOK;
import static com.sansyro.sgpspring.constants.NotificationTypeEnum.INSTAGRAM;
import static com.sansyro.sgpspring.constants.NotificationTypeEnum.SITE;
import static com.sansyro.sgpspring.constants.NotificationTypeEnum.SMS;
import static com.sansyro.sgpspring.constants.NotificationTypeEnum.WHATAPP;
import static com.sansyro.sgpspring.constants.StringConstaint.AUTHORIZATION;
import static com.sansyro.sgpspring.constants.StringConstaint.BEARER;
import static com.sansyro.sgpspring.constants.StringConstaint.NAME;
import static com.sansyro.sgpspring.constants.StringConstaint.PASSWORD_DEFAULT;
import static com.sansyro.sgpspring.constants.TypeLotteryEnum.DUPLA_SENA;
import static com.sansyro.sgpspring.constants.TypeLotteryEnum.LOTECA;
import static com.sansyro.sgpspring.constants.TypeLotteryEnum.LOTOFACIL;
import static com.sansyro.sgpspring.constants.TypeLotteryEnum.LOTOMANIA;
import static com.sansyro.sgpspring.constants.TypeLotteryEnum.MEGA_SENA;
import static com.sansyro.sgpspring.constants.TypeLotteryEnum.QUINA;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.sansyro.sgpspring.constants.StringConstaint;
import org.junit.jupiter.api.Test;

public class ConstaintTest {

    @Test
    void StringConstaintTest() {
        assertNotNull(new StringConstaint());
        assertNotNull(PASSWORD_DEFAULT);
        assertNotNull(BEARER);
        assertNotNull(AUTHORIZATION);
        assertNotNull(NAME);
    }

    @Test
    void TypeLotteryEnumTest() {
        assertNotNull(MEGA_SENA.getValue());
        assertNotNull(LOTOFACIL.getValue());
        assertNotNull(QUINA.getValue());
        assertNotNull(LOTOMANIA.getValue());
        assertNotNull(DUPLA_SENA.getValue());
        assertNotNull(LOTECA.getValue());
    }

    @Test
    void NotificationTypeEnumTest() {
        assertNotNull(SITE);
        assertNotNull(SMS);
        assertNotNull(EMAIL);
        assertNotNull(WHATAPP);
        assertNotNull(FACEBOOK);
        assertNotNull(INSTAGRAM);
    }

}
