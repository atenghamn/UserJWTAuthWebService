package com.example.userjwtauthwebservice.auth.controller;


import com.example.userjwtauthwebservice.auth.domain.JwtUser;
import com.example.userjwtauthwebservice.auth.dto.AuthenticationToken;
import com.example.userjwtauthwebservice.auth.dto.Credentials;
import com.example.userjwtauthwebservice.auth.service.JwtService;
import com.example.userjwtauthwebservice.exception.NotAuthorizedException;
import com.example.userjwtauthwebservice.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import static com.example.userjwtauthwebservice.auth.domain.SecurityConstants.EXPIRATION_TIME;

@Slf4j
@RestController
@RequestMapping("api/authenticate")
public class AuthController {

    private static final String INVALID_USERNAME_OR_PASSWORD = "Felaktigt användarnamn eller lösenord!";
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder, JwtService jwtService){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @PostMapping(path="/login")
    public ResponseEntity<AuthenticationToken>  logIn(@Valid @RequestBody Credentials credentials){
        log.debug("Attempting to login {}", credentials.getUsername());

        var user = userService.getByUsername(credentials.getUsername());

        if(!passwordEncoder.matches(credentials.getPassword(), user.getPassword())){
            log.warn("Login attempt using invalid password from account {}!", credentials.getUsername());
            throw new NotAuthorizedException(INVALID_USERNAME_OR_PASSWORD);
        }

        return ResponseEntity.ok(
                AuthenticationToken.builder()
                .accessToken(jwtService.encodeJwt(
                        JwtUser.builder()
                                .id(user.getId())
                                .username(user.getUsername())
                        .build()))
                .expiresIn(Long.toString(EXPIRATION_TIME))
                .tokenType("Bearer")
                .build());
    }
}
