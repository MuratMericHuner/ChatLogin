package com.example.spring.security.jwt.Repo;


import com.example.spring.security.jwt.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
        User findByEmail(String email);
        @Query("SELECT u FROM User u WHERE u.email =?1")
        Optional<User> existsByMail(String email);

        @Query("SELECT u FROM User u WHERE u.username =?1")
        Optional<User> existByName(String name);
}
