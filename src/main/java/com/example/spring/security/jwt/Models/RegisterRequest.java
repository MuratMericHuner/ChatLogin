package com.example.spring.security.jwt.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Data
public class RegisterRequest {

    private String username;
    private String email;
    private String password;
}
