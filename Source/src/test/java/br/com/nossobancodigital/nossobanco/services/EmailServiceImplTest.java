package br.com.nossobancodigital.nossobanco.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTest {
    private static final String TO = "arthur.camargo@hotmail.com.br";
    private static final String SUBJECT = "Email's test subject";
    private static final String MESSAGE = "Email's test message.";
    private static final String EXCEPTION_MESSAGE = "Exception message.";
    private static final Boolean SUCCESSFUL_SEND_RETURN = true;
    private static final Boolean UNSUCCESSFULLY_SEND_RETURN = false;

    @Mock
    private JavaMailSender emailSender; // = mock(JavaMailSender.class);

    @InjectMocks
    private EmailServiceImpl emailService;

    @Test
    @DisplayName("Succeeded to send an email.")
    void validateFirstStepRegistrationSuccess() {
        doNothing().when(emailSender).send(isA(SimpleMailMessage.class));

        Boolean response = emailService.sendSimpleMessage(TO, SUBJECT, MESSAGE);

        assertThat(response, is(SUCCESSFUL_SEND_RETURN));
    }

    @Test
    @DisplayName("Failed when sending an email: MailParseException.")
    void validateFirstStepRegistrationParseExceptionFail() {
        doThrow(new MailParseException(EXCEPTION_MESSAGE)).when(emailSender).send(isA(SimpleMailMessage.class));

        Boolean response = emailService.sendSimpleMessage(TO, SUBJECT, MESSAGE);

        assertThat(response, is(UNSUCCESSFULLY_SEND_RETURN));
    }

    @Test
    @DisplayName("Fail when sending an email: MailAuthenticationException.")
    void validateFirstStepRegistrationMailAuthenticationExceptionFail() {
        doThrow(new MailAuthenticationException(EXCEPTION_MESSAGE)).when(emailSender).send(isA(SimpleMailMessage.class));

        Boolean response = emailService.sendSimpleMessage(TO, SUBJECT, MESSAGE);

        assertThat(response, is(UNSUCCESSFULLY_SEND_RETURN));
    }

    @Test
    @DisplayName("Fail when sending an email: MailSendException.")
    void validateFirstStepRegistrationMailSendExceptionFail() {
        doThrow(new MailSendException(EXCEPTION_MESSAGE)).when(emailSender).send(isA(SimpleMailMessage.class));

        Boolean response = emailService.sendSimpleMessage(TO, SUBJECT, MESSAGE);

        assertThat(response, is(UNSUCCESSFULLY_SEND_RETURN));
    }
}