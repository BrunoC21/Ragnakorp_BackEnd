package com.Polo.repository;

import com.Polo.model.User;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUserRut(String userRut);

    User findByUserName(String userName);

    Optional<User> findByUserNameAndUserRut(String userName, String userRut);
}
