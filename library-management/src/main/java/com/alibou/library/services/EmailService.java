package com.alibou.library.services;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender  mailSender;
    private final SpringTemplateEngine templateEngine;

    @Async
    public void sendEmail(String to,
                          String subject,
                          String username, // Corrected parameter name
                          EmailTempelate emailTempelate,
                          String confirmationUrl,
                          String activationCode, String text) throws MessagingException {

        String templateName;
        if (emailTempelate == null) {
            templateName = "confirm-email";
        } else {
            templateName = emailTempelate.name().toLowerCase();
        }

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(
                mimeMessage,
                MimeMessageHelper.MULTIPART_MODE_MIXED,
                StandardCharsets.UTF_8.name()
        );

        Map<String, Object> properties = new HashMap<>();
        properties.put("username", username); // Use the corrected parameter
        properties.put("confirmationUrl", confirmationUrl);
        properties.put("activationCode", activationCode); // Corrected key name
        properties.put("text", text);

        Context context = new Context();
        context.setVariables(properties);

        helper.setFrom("enochsarkodie07@gmail.com");
        helper.setTo(to);
        helper.setSubject(subject);

        String template = templateEngine.process(templateName, context);

        helper.setText(template, true);

        mailSender.send(mimeMessage);
    }
}