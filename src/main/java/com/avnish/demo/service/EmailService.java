package com.avnish.demo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class EmailService {


    private JavaMailSender javaMailSender;

    public void sendVerificationEmail(String email, String otp) throws MessagingException {
       MimeMessage mimeMessage = javaMailSender.createMimeMessage();
       MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,"utf-8");

       String subject = "Verification Otp Email";
       String text="Your verification code is "+otp;

       mimeMessageHelper.setSubject(subject);
       mimeMessageHelper.setText(text);
        mimeMessageHelper.setTo(email);

        try{
            javaMailSender.send(mimeMessage);
        }
        catch(MailException mex){
            throw new MailSendException(mex.getMessage());
        }



    }

}
