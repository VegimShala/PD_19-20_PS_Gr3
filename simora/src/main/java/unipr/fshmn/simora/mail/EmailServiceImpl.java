package unipr.fshmn.simora.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl  {

    @Autowired
    public JavaMailSender emailSender;

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("no-reply-simora@uni-pr.edu");
        message.setSubject(subject);
        message.setText(text);
        try {
            emailSender.send(message);
        }catch(MailException e){
            System.out.println("Couldn't send email to "+to);
        }
    }
}