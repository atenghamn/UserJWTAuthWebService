package com.example.userjwtauthwebservice.auth.service;

import com.example.userjwtauthwebservice.auth.domain.TokenWrapper;
import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface TokenAuthenticationService {

    TokenWrapper getTokenWrapper(String authHeader);
    Authentication authenticate(HttpServletRequest request);
    Authentication authenticate(String token);
    Authentication authenticate(TokenWrapper token);
}
