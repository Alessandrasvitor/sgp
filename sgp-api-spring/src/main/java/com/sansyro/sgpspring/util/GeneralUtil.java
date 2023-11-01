package com.sansyro.sgpspring.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sansyro.sgpspring.constants.TypeLotteryEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.text.MessageFormat;
import java.util.*;

import static java.util.Objects.isNull;

@Slf4j
public class GeneralUtil {

    public static Boolean stringNullOrEmpty(String valor ){
        return isNull(valor) || valor.isEmpty() || valor.length() == 0;
    }

    public static String getNewCode() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    public static String getBet(TypeLotteryEnum typeLottery) {
        Set<Integer> values = new HashSet<>();
        StringBuilder bet = new StringBuilder();
        do {
            values.add(RandomUtils.nextInt(0, typeLottery.getMax()));
        } while (values.size() < typeLottery.getQuantity());
        List<Integer> arrayList = new ArrayList(values);
        Collections.sort(arrayList);
        arrayList.forEach(value -> {
            if(value <= 9) {
                bet.append("0");
            }
            bet.append(value+"-");
        });

        return bet.substring(0, bet.length() - 1);
    }

    public static String getMessageExeption(String msg, String... params) {
        MessageFormat messageFormat = new MessageFormat(msg);
        return messageFormat.format(params);
    }

    public static String generatedStringObject(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.info("Não foi possível converter o objeto do tipo {} para String.", obj.getClass());
            return "";
        }
    }

}
