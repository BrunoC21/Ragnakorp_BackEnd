package com.Polo.repository;

import com.Polo.model.*;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUserRut(String userRut);

    Optional<User> findByUserName(String userName);

    Optional<User> findByUserEmail(String email);

    Optional<User> findByUserNameAndUserRut(String userName, String userRut);
}
