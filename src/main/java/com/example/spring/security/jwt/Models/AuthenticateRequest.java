package com.example.spring.security.jwt.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@AllArgsConstructor
@SuperBuilder
@NoArgsConstructor
@Data
public class AuthenticateRequest {
    private String email;
    String password;
}
