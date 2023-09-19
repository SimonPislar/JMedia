package com.JMedia.Communication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSender {

    private final JavaMailSender javaMailSender;

    @Autowired
    public EmailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    /*
        @Brief: This method is used to email a user.
        @Param: destinationAddress - The email address of the user to send the email to.
        @Param: subject - The subject of the email.
        @Param: content - The content of the email.
        @Return: void - Returns nothing.
    */
    public void send(String destinationAddress, String subject, String content) {
        System.out.println("Sending email to: " + destinationAddress + ", subject: " + subject);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(destinationAddress);
        message.setSubject(subject);
        message.setText(content);

        javaMailSender.send(message);
    }

}
