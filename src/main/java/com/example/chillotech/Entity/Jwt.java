package com.example.chillotech.Entity;

import com.example.chillotech.TypeRole;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

/**
 * Avis
 */
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jwt")
public class Jwt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String valeur;
    private boolean desactive;
    private boolean isExpired;

    @ManyToOne(cascade ={CascadeType.DETACH, CascadeType.MERGE})
    @JoinColumn(name = "utilisateur_id")
    private User user;

}