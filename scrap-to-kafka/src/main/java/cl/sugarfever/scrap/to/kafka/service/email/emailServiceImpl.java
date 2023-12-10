//package com.scraping.tcero.email;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.mail.javamail.JavaMailSenderImpl;
//
//import java.util.Properties;
//
//public class emailServiceImpl {
//
//    private static final Logger log = LoggerFactory.getLogger(emailServiceImpl.class);
//
//    @Value("${email.username}")
//    public String user;
//
//    @Value("${email.password}")
//    public String pass;
//
//    public void sendSimpleMessage(String to, String subject, String text) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(to);
//        message.setSubject(subject);
//        message.setText(text);
//        getJavaMailSender() .send(message);
//    }
//
//    public JavaMailSender getJavaMailSender() {
//        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
//        mailSender.setHost("smtp.gmail.com");
//        mailSender.setPort(587);
//
//        log.info("mail: " + user);
//        log.info("pass: " + pass);
//        mailSender.setUsername(user);
//        mailSender.setPassword(pass);
//
//        Properties props = mailSender.getJavaMailProperties();
//        props.put("mail.transport.protocol", "smtp");
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.debug", "true");
//
//        return mailSender;
//    }
//}
