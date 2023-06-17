package com.iksad.simpluencer.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EmailRepositoryImpl implements EmailRepository {
    private final JavaMailSender mailSender;

    @Override
    public void send(String address, String title, String content)  throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(address);
        message.setSubject(title);
        message.setText(content);
        mailSender.send(message);
    }
}
