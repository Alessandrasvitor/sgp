package com.sansyro.sgpspring.util;

import org.apache.commons.lang3.RandomStringUtils;

public class GeralUtil {

    public static Boolean stringNullOrEmpty(String valor ){
        return valor == null || valor.isEmpty() || valor.length() == 0;
    }

    public static String getNewHashCode() {
        return RandomStringUtils.randomAlphabetic(10);
    }
}
