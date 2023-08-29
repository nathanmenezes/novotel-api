package com.meinz.novotelapi.util;

import com.meinz.novotelapi.model.EmailToken;
import com.meinz.novotelapi.model.User;
import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.util.Properties;

public class EmailUtils {

    public static void sendEmail(User user, EmailToken emailToken) {
        final String activationLink = "http://localhost:4200/v1/users/activate/" + emailToken.getToken();
        final String htmlContent = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Ativação de Conta</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h2>Ativação de Conta</h2>\n" +
                "    <p>Olá, " + user.getName() + "</p>\n" +
                "    <p>Clique no link abaixo para ativar sua conta:</p>\n" +
                "    <a href=\"" + activationLink + "\">Ativar Conta</a>\n" +
                "    <p>Se você não solicitou a ativação, ignore este email.</p>\n" +
                "</body>\n" +
                "</html>";
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("oficialstylesync@gmail.com", "xbytpbrgulwojutj");
                    }
                });
        session.setDebug(true);

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("oficialstylesync@gmail.com"));

            Address[] toUser = InternetAddress
                    .parse(user.getEmail());

            message.setRecipients(Message.RecipientType.TO, toUser);
            message.setSubject("Ativação de Conta - Style Sync");
            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
