package com.example.userjwtauthwebservice.auth.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.Key;
import java.util.List;

import static com.example.userjwtauthwebservice.auth.domain.SecurityConstants.*;
@Component
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final Key key = new SecretKeySpec(
            DatatypeConverter.parseBase64Binary(SECRET),
            SignatureAlgorithm.HS512.getJcaName());

    private final UserDetailsService userDetailsService;

    public JWTAuthorizationFilter(UserDetailsService userDetailsService){
        this.userDetailsService = userDetailsService;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {

        var header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }
        var authentication = getAuthentication(req);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        var token = request.getHeader(HEADER_STRING);
        if (token != null) {
            var claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token.substring(7));
            var username = claims.getBody().getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            var authority = claims.getBody().get("authority");

            if (userDetails != null) {
                return new UsernamePasswordAuthenticationToken(userDetails, null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + authority)));
            }

            return null;
        }
        return null;
    }

}
