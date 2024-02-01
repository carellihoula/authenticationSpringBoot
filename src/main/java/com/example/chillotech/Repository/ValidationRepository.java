package com.example.chillotech.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.chillotech.Entity.Validation;

public interface ValidationRepository extends CrudRepository<Validation, Integer>{

    Optional<Validation> findByCode(String code);
    
}
