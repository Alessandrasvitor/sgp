package com.sansyro.sgpspring.unit;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sansyro.sgpspring.exception.ServiceException;
import com.sansyro.sgpspring.util.DateUtil;
import com.sansyro.sgpspring.util.EmailUtil;
import com.sansyro.sgpspring.util.GeneralUtil;
import com.sansyro.sgpspring.util.SecurityUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

public class UtilTest {

    @Test
    void GeneralUtilTest() {
        assertTrue(GeneralUtil.stringNullOrEmpty(null));
        assertTrue(GeneralUtil.stringNullOrEmpty(RandomStringUtils.randomAlphabetic(0)));
        assertFalse(GeneralUtil.stringNullOrEmpty(RandomStringUtils.randomAlphabetic(10)));
    }

    @Test
    void DateUtilTest() {
        assertNull(DateUtil.formatDate(null, ""));
        assertThrows(ServiceException.class, () -> DateUtil.convertToDate(RandomStringUtils.randomAlphabetic(10)));
    }

    @Test
    void ConstructorUtilTest() {
        new DateUtil();
        new SecurityUtil();
        new GeneralUtil();
        new EmailUtil();
    }

}
