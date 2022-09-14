package com.example.userjwtauthwebservice.auth.service;

import com.example.userjwtauthwebservice.auth.domain.TokenWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class JsonWebTokenAuthenticationService implements TokenAuthenticationService {
    @Override
    public TokenWrapper getTokenWrapper(String authHeader) {
        // Check if token exist, or else not allowed
        if (Objects.isNull(authHeader)) {
            log.info("An application that is not authenticated tried to log in!");
            return null;
        }

        // Check if it's an application, or else not allowed
        return new TokenWrapper(authHeader);
    }

    @Override
    public Authentication authenticate(TokenWrapper token) {

        // Authenticate the user since the JWT is correct.
        var authentication = new UsernamePasswordAuthenticationToken(token.getUserId(), null,
                List.of(new SimpleGrantedAuthority("ROLE_USER")));
        authentication.setDetails(token);
        return authentication;
    }

    @Override
    public Authentication authenticate(String authHeader) {
        var token = getTokenWrapper(authHeader);
        return authenticate(token);
    }

    @Override
    public Authentication authenticate(HttpServletRequest request) {
        return authenticate(request.getHeader("Authorization"));
    }
}
