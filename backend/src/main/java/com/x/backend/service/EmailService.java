package com.x.backend.service;

import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Resource
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    public void sendEmail(String to, String subject, String text) {
        try {
            // 创建邮件消息
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);               // 发件人
            message.setTo(to);                   // 收件人
            message.setSubject(subject);         // 邮件主题
            message.setText(text);               // 邮件内容

            // 发送邮件
            mailSender.send(message);
            System.out.println("邮件发送成功！");
        } catch (Exception e) {
            System.err.println("邮件发送失败：" + e.getMessage());
            throw new RuntimeException("邮件发送失败", e);
        }
    }
}
