package com.example.chillotech.Service;

import com.example.chillotech.Entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.chillotech.Entity.Avis;
import com.example.chillotech.Repository.AvisRepository;

import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
import lombok.NonNull;

@Service
@AllArgsConstructor
//@NoArgsConstructor
public class AvisService {
    private final AvisRepository avisRepository;

    
    public void creer( Avis avis) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        avis.setUser(user);
        this.avisRepository.save(avis);
    }

}
