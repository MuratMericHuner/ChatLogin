package com.example.spring.security.jwt.Service;

import com.example.spring.security.jwt.Models.AuthenticateRequest;
import com.example.spring.security.jwt.Models.RegisterRequest;
import com.example.spring.security.jwt.Models.HttpResponse;
import com.example.spring.security.jwt.Models.Roles;
import com.example.spring.security.jwt.Models.User;
import com.example.spring.security.jwt.Repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    public HttpResponse authenticate(AuthenticateRequest authenticateRequest) {
        if(userRepo.existsByMail(authenticateRequest.getEmail()).isEmpty())  return HttpResponse.builder().message("There is no user with that mail").status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build();
        if(!passwordEncoder.matches(authenticateRequest.getPassword(), userRepo.findByEmail(authenticateRequest.getEmail()).getPassword())) return HttpResponse.builder().message("Wrong Password").status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticateRequest.getEmail(), authenticateRequest.getPassword()));
        User user = userRepo.findByEmail(authenticateRequest.getEmail());
        String jwtToken= jwtService.generateToken(user);
        return HttpResponse.builder().message("User has authenticated").status(HttpStatus.FOUND).statusCode(HttpStatus.FOUND.value()).token(jwtToken).data(Map.of("User", user)).build();
    }

    public HttpResponse register(RegisterRequest registerRequest) {
        if(userRepo.existsByMail(registerRequest.getEmail()).isPresent())  return HttpResponse.builder().message("A user already exists with that mail").status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build();
        if(userRepo.existByName(registerRequest.getUsername()).isPresent())  return HttpResponse.builder().message("A user already exists with that username").status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build();
        User user = User.builder().username(registerRequest.getUsername()).email(registerRequest.getEmail()).password(passwordEncoder.encode(registerRequest.getPassword()))
                .role(Roles.USER).build();
        userRepo.save(user);
        String jwtToken= jwtService.generateToken(user);
        return HttpResponse.builder().message("User has been registered").status(HttpStatus.CREATED).statusCode(HttpStatus.CREATED.value()).token(jwtToken).data(Map.of("User", user)).build();
    }

    public HttpResponse getAllUsers(){
        List<String> users =userRepo.findAll().stream().map(User::getUsername).toList();
        return HttpResponse.builder().message("Users").status(HttpStatus.FOUND).statusCode(HttpStatus.FOUND.value()).data(Map.of("Users", users)).build();
    }
}
