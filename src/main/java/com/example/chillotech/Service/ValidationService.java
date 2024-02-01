package com.example.chillotech.Service;

import java.time.Instant;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.chillotech.Entity.User;
import com.example.chillotech.Entity.Validation;
import com.example.chillotech.Repository.ValidationRepository;

import  static java.time.temporal.ChronoUnit.MINUTES;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ValidationService {

    private ValidationRepository validationRepository;
    private NotificationService notificationService;

    public void enregistrer(User user) {
        Validation validation = new Validation();
        validation.setUser(user);
        Instant creation = Instant.now();
        validation.setCreation(creation);
        Instant expiration = creation.plus(10, MINUTES);
        validation.setExpiration(expiration);

        //generate code for validation
        Random random = new Random();
        Integer decimal = random.nextInt(999999);
        String code  = String.format("%06d", decimal);
        validation.setCode(code);

        this.validationRepository.save(validation);
        notificationService.envoyer(validation);
        
    }
    public Validation lireEnFonctionDuCode(String code) {
        return this.validationRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Code invalide"));
    }
}
