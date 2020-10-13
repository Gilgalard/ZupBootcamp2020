package br.com.nossobancodigital.nossobanco.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender emailSender;

    private static final String MAIL_FROM = "my@email.com";

    @Override
    public Boolean sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(MAIL_FROM);
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);

        try {
            emailSender.send(simpleMailMessage);
        } catch (MailException mailException) {
            // Here we could run some specific treatments for the exceptions... Maybe using some log services?
            // For now, I'll return only false to return an specific error code through the controller.
            return false;
        }

        return true;
    }
}