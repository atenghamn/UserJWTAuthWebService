package com.example.userjwtauthwebservice.service;

import org.javatuples.Pair;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    private final Pair<Integer, String> badCredentials = Pair.with(401, "Incorrect username or password");

    public AuthService(JwtUtils jwtUtils, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<String> authenticate(String username, String password){

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        if(!passwordEncoder.matches(password, userDetails.getPassword()))
            return ResponseEntity.status(badCredentials.getValue0()).body(badCredentials.getValue1());

        return ResponseEntity.ok(jwtUtils.generateToken(username));
    }

    public boolean validate(String token){
        return jwtUtils.validate(token);
    }

}
