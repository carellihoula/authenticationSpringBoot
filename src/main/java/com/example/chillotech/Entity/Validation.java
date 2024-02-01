package com.example.chillotech.Entity;

import java.time.Instant;

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
 * Validation
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "validation")
public class Validation {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;

private Instant expiration;
private Instant activation;
private Instant creation;
private String code;

@OneToOne(cascade = CascadeType.ALL)
private User user;
    
}