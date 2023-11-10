package com.sansyro.sgpspring.util;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_EMAIL_INVALID;

import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.exception.ServiceException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailUtil {

    public static void isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            throw new ServiceException(GeneralUtil.getMessageExeption(MSG_EMAIL_INVALID.getMessage(), email), MSG_EMAIL_INVALID.getCode());
        }
    }

    public static void sendMail(User user) {
        log.info("Usuários de email ", user.getEmail());
    }


}
