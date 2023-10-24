package com.sansyro.sgpspring.util;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

import java.util.HashSet;
import java.util.Set;

import static java.util.Objects.isNull;

public class GeralUtil {

    public static Boolean stringNullOrEmpty(String valor ){
        return isNull(valor) || valor.isEmpty() || valor.length() == 0;
    }

    public static String getNewHashCode() {
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String getBet(int index, int max) {
        StringBuilder bet = new StringBuilder();
        Set numbers = new HashSet<Integer>();
        do {
            numbers.add(RandomUtils.nextInt(0, max));
        } while (numbers.size() < index);
        numbers.forEach(number -> bet.append(number));
        return bet.toString();
    }

}
