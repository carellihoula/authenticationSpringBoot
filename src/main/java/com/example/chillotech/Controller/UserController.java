package com.example.chillotech.Controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.chillotech.Entity.User;
import com.example.chillotech.Security.JwtService;
import com.example.chillotech.Service.UserService;
import com.example.chillotech.dto.AuthenticationDto;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private AuthenticationManager authenticationManager;
    private UserService userService;
    private JwtService jwtService;

    @PostMapping(path="/inscription")
    public void inscription(@RequestBody User user){
        log.info("inscription");
        this.userService.inscription(user);
    }

    @PostMapping(path="/activation")
    public void activation(@RequestBody Map<String, String> activation){
        this.userService.activation(activation);
    }

    @PostMapping(path="/connexion")                                                                       
    public Map<String, String> connexion(@RequestBody AuthenticationDto authenticationDto){
        Authentication authenticate = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                authenticationDto.username(), 
                authenticationDto.password())
        );
        //log.info("result {}", authenticate.isAuthenticated());
        if(authenticate.isAuthenticated()){
            return jwtService.generate(authenticationDto.username());
        }
        return null;
    }
    @PostMapping(path="/deconnexion")
    public void deconnexion(){
        this.jwtService.deconnexion();
    }


}
