package com.example.chillotech.Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.chillotech.Entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
