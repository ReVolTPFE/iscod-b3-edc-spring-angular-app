package com.asteiner.edc.integration.Service;

import com.asteiner.edc.Service.EmailService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith(SpringExtension.class)
public class EmailServiceImplIT {
    @Autowired
    private EmailService emailService;

    @MockBean
    private JavaMailSender mailSender;

    @Test
    public void testSendEmail() {
        String toEmail = "user@example.com";
        String subject = "New task assigned";
        String body = "You have been assigned to a new task.";

        emailService.sendEmail(toEmail, subject, body);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        Mockito.verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();

        Assertions.assertThat(sentMessage.getTo()).containsExactly(toEmail);
        Assertions.assertThat(sentMessage.getSubject()).isEqualTo(subject);
        Assertions.assertThat(sentMessage.getText()).isEqualTo(body);
        Assertions.assertThat(sentMessage.getFrom()).isEqualTo("asteiner@visiplus.com");
    }
}
