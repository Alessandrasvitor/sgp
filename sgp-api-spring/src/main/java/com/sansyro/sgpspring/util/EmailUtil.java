package com.sansyro.sgpspring.util;

import com.sansyro.sgpspring.constants.MessageEnum;
import com.sansyro.sgpspring.entity.User;
import com.sansyro.sgpspring.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import static com.sansyro.sgpspring.constants.MessageEnum.MSG_EMAIL_INVALID;

@Slf4j
public class EmailUtil {

    public static boolean isValidEmailAddress(String email) {
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
            return true;
        } catch (AddressException ex) {
            throw new ServiceException(GeneralUtil.getMessageExeption(MSG_EMAIL_INVALID.getMessage(), email), MSG_EMAIL_INVALID.getCode());
        }
    }

    public static void sendMail(User user) {
        Properties props = getProperties();
        Session session = getSession(props);

        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("ajenterpricesa@gmail.com", "Sistema de Gestão Pessoal"));
            msg.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(user.getEmail(), user.getName()));
            msg.setSubject("Your Example.com account has been activated");
            msg.setHeader("X-Mailer", "My Simple JavaMail Client");
            msg.setText("This is a test");
            Transport.send(msg);
        } catch (AddressException e) {
            log.error("Erro: ", e);
        } catch (MessagingException e) {
            log.error("Erro: ", e);
        } catch (UnsupportedEncodingException e) {
            log.error("Erro: ", e);
        }

    }

    private static Session getSession(Properties props) {
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("ajenterpricesa@gmail.com",
                                "SansyroSGP");
                    }
                });
        session.setDebug(true);
        return session;
    }

    private static Properties getProperties() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");

        return props;
    }


}
