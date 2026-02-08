package com.vnote.VNote.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailServiceTests {
    @Autowired
    private MailService mailService;


    @Test
    void sendMailTest() {
        this.mailService.sendSimpleMail("uddeshyasingh12bsci@gmail.com", "Hello, Bitch!", "I'm Java, sending you some shit!");
    }
}
