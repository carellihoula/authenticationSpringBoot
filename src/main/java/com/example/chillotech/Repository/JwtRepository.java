package com.example.chillotech.Repository;

import com.example.chillotech.Entity.Jwt;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

public interface JwtRepository extends CrudRepository<Jwt, Integer> {

    Optional<Jwt> findByValeurAndDesactiveAndIsExpired(String valeur, boolean desactive, boolean expire);


    @Query("FROM Jwt j WHERE j.user.email = :email AND j.desactive = :desactive AND j.isExpired = :expire")
    Optional<Jwt> findUserValidToken(String email, boolean desactive, boolean expire);

    //trouver un user à travers son e-mail
    @Query("FROM Jwt j WHERE j.user.email = :email")
    Stream<Jwt> findUser(String email);
}
