/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bootcamp.assetmanagement.tools;

import javax.management.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 *
 * @author Lenovo
 */
@Service
public class SendEmail{
    
    private JavaMailSender javaMailSender;

    @Autowired
    public SendEmail(JavaMailSender javaMailSender)throws MailException{
        this.javaMailSender = javaMailSender;
    }
    
    public void sendEmail(String sendTo, String subject, String content) {

        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setTo(sendTo);

        msg.setSubject(subject);
        msg.setText(content);
        javaMailSender.send(msg);

    }
}
