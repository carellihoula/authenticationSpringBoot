package com.example.chillotech.Service;

import java.time.Instant;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.chillotech.TypeRole;
import com.example.chillotech.Entity.Role;
import com.example.chillotech.Entity.User;
import com.example.chillotech.Entity.Validation;
import com.example.chillotech.Repository.UserRepository;

import lombok.AllArgsConstructor;


@Service
@AllArgsConstructor
public class UserService implements UserDetailsService{
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;
    private ValidationService validationService;

    public void inscription(User user){
        String mdpcrypte = passwordEncoder.encode(user.getPassword());
        user.setPassword(mdpcrypte);
        if(!user.getEmail().contains("@") && !user.getEmail().contains(".")){
            throw new RuntimeException("Email invalide");
        }

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email existe deja");
        }
        Role roleUser = new Role();
        roleUser.setLibelle(TypeRole.USER);
        user.setRole(roleUser);
        this.userRepository.save(user);
        validationService.enregistrer(user);
    }

    public void activation(Map<String, String> activation) {
        // TODO Auto-generated method stub

        Validation validation = validationService.lireEnFonctionDuCode(activation.get("code"));

        if(Instant.now().isAfter(validation.getExpiration())){
            throw new RuntimeException("Code expiré");
        }

        if(validation.getCode().equals(activation.get("code"))){
            User user = validation.getUser();
            user.setActif(true);

            //moment ou le compte est activé
            validation.setActivation(Instant.now());

            this.userRepository.save(user);
        }
        else{
            throw new RuntimeException("Code invalide");
        }
        
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        return this.userRepository.findByEmail(username).orElseThrow(() -> 
        new UsernameNotFoundException("Aucun utilisateur n'a été trouvé"));
    }
    
}
