package com.mcrmk.springboot.ecommerce.mail;

public interface EmailService {

    void sendSimpleMessage(String to,
                           String subject,
                           String text);

}
