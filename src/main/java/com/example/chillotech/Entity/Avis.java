package com.example.chillotech.Entity;

import jakarta.persistence.*;
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
@Table(name = "avis")
public class Avis {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
private String message;
private String statut;
@ManyToOne
private User user;
    
}