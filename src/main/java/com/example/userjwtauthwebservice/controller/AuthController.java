package com.example.userjwtauthwebservice.controller;

import com.example.userjwtauthwebservice.dto.JwtRequest;
import com.example.userjwtauthwebservice.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    public final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<String>  logIn(@RequestBody JwtRequest jwtRequest){
        return authService.authenticate(jwtRequest.username(), jwtRequest.password());
    }
}
