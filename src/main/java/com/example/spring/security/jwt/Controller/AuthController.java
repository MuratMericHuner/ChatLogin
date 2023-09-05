package com.example.spring.security.jwt.Controller;

import com.example.spring.security.jwt.Models.AuthenticateRequest;
import com.example.spring.security.jwt.Models.HttpResponse;
import com.example.spring.security.jwt.Models.RegisterRequest;
import com.example.spring.security.jwt.Service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody RegisterRequest registerRequest){
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<HttpResponse> authenticate(@RequestBody AuthenticateRequest authenticateRequest){
        return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
    }

    @GetMapping("/users")
    public ResponseEntity<HttpResponse> getUsers(){
        return ResponseEntity.ok(authenticationService.getAllUsers());
    }
}
