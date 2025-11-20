package org.fizzy.busywithfish.dataserver.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendVerificationCode(String to, String code) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("Busy With Fish 电子邮件验证码");
        message.setText("你的验证码是：" + code + "\n\n这个验证码将在5分钟后过期。");
        
        javaMailSender.send(message);
    }
}