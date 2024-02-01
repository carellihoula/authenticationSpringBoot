package com.example.chillotech.Entity;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Avis
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User implements UserDetails {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String password;
private String email;
private String nom;
private boolean actif = false;

@OneToOne(cascade = CascadeType.ALL)
private Role role;

@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    // TODO Auto-generated method stub
    return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"+this.role.getLibelle()));
}

@Override
public String getPassword() {
    // TODO Auto-generated method stub
    return this.password;
}

@Override
public String getUsername() {
    // TODO Auto-generated method stub
    return this.email;
}

@Override
public boolean isAccountNonExpired() {
    // TODO Auto-generated method stub
    return this.actif;
}

@Override
public boolean isAccountNonLocked() {
    // TODO Auto-generated method stub
    return this.actif;
}

@Override
public boolean isCredentialsNonExpired() {
    // TODO Auto-generated method stub
    return this.actif;
}

@Override
public boolean isEnabled() {
    // TODO Auto-generated method stub
    return this.actif;
}


    
}