package com.sansyro.sgpspring.util;

import com.sansyro.sgpspring.exception.ServiceException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_UNABLE_CONVERT_DATE;
import static java.util.Objects.isNull;

public class DateUtil {
    public static final String FORMAT_DEFAULT_DATE = "dd-MM-yyyy";
    private DateUtil() {
    }

    public static String formatDate(Date date, String pattern) {
        if(isNull(date)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String formatDate(Date date) {
        if(isNull(date)) {
            return null;
        }
        return formatDate(date, FORMAT_DEFAULT_DATE);
    }

    public static Date convertToDate(String date) {
        if(GeneralUtil.stringNullOrEmpty(date)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DEFAULT_DATE);
            return sdf.parse(date);
        } catch (ParseException e) {
            throw new ServiceException(GeneralUtil.getMessageExeption(MSG_UNABLE_CONVERT_DATE.getMessage(), date), MSG_UNABLE_CONVERT_DATE.getCode());
        }
    }

}
