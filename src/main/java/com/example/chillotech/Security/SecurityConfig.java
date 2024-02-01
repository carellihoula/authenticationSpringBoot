package com.example.chillotech.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public SecurityConfig(JwtFilter jwtFilter, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.jwtFilter = jwtFilter;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.POST,"/inscription").permitAll()
                .requestMatchers(HttpMethod.POST,"/activation").permitAll()
                .requestMatchers(HttpMethod.POST,"/avis").permitAll()
                .requestMatchers(HttpMethod.POST,"/connexion").permitAll()
                .anyRequest().authenticated()
            )
               .sessionManagement(httpSecuritySessionManagementConfigurer ->
                       httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               )

               .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
               .build();
            
    }



    @Bean 
    public AuthenticationProvider authenticationProvider (UserDetailsService userDetailsService){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailsService);
        dao.setPasswordEncoder(this.bCryptPasswordEncoder);
        return dao;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) 
    throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }
    
    /*@Bean public UserDetailsService userDetailsService() {
        return new UserService();
    }*/

    

   
}
