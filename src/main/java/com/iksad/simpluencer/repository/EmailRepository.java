package com.iksad.simpluencer.repository;

import org.springframework.mail.MailException;

public interface EmailRepository {
    void send(String address, String title, String content)  throws MailException;
}