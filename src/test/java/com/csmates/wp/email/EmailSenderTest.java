package com.csmates.wp.email;

import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.util.GreenMailUtil;
import com.icegreen.greenmail.util.ServerSetupTest;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Message;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO this test uses not in-memory database, it must use h2 database
@SpringBootTest
class EmailSenderTest {
    @RegisterExtension
    static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
            .withConfiguration(GreenMailConfiguration.aConfig().withUser("koalaa13", "password"))
            .withPerMethodLifecycle(false);

    @TestConfiguration
    static class TestConfig {
        @Bean
        public JavaMailSenderImpl javaMailSender() {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

            // TODO move it to prop file
            mailSender.setHost("localhost");
            mailSender.setPort(3025);

            mailSender.setUsername("koalaa13");
            mailSender.setPassword("password");

            Properties props = mailSender.getJavaMailProperties();
            props.put("mail.transport.protocol", "smtp");

            return mailSender;
        }
    }

    @Autowired
    private EmailSenderImpl emailSender;

    @SneakyThrows
    @Test
    void commonConfirmationEmailTest() {
        String[] texts = {
                "test",
                "<b>test</b>",
                ""
        };
        final String receiver = "test@receiver.com";
        for (String text : texts) {
            emailSender.send(receiver, text);
        }

        Message[] messages = greenMail.getReceivedMessages();
        assertEquals(texts.length, messages.length);

        for (int i = 0; i < texts.length; ++i) {
            assertEquals("Confirm your email", messages[i].getSubject());
            String body = GreenMailUtil.getBody(messages[i]);
            assertEquals(texts[i], body);
        }
    }
}
