package com.example.chillotech.Service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.chillotech.Entity.Validation;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    JavaMailSender javaMailSender;

    public void envoyer(Validation validation) {

        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setFrom("noreply@carel.com");
        mail.setTo(validation.getUser().getEmail());
        mail.setSubject("votre code d'activation");

        String content  = String.format("Bonjour %s,\n" +
                "Votre code d'activation est %s\n" +
                "Ce code expire dans 10 minutes", validation.getUser().getNom(), validation.getCode());

        mail.setText(content);
        javaMailSender.send(mail); 
        //System.out.println(content);
    }
}
