package com.example.userjwtauthwebservice.auth.service;

import com.example.userjwtauthwebservice.auth.domain.JwtUser;
import com.example.userjwtauthwebservice.exception.NotAuthorizedException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

import static com.example.userjwtauthwebservice.auth.domain.SecurityConstants.*;

@Service
public class JwtService {
    private static final Key key = new SecretKeySpec(
            DatatypeConverter.parseBase64Binary(SECRET),
            SignatureAlgorithm.HS512.getJcaName());

    public void isAdministrator(Integer userId, HttpServletRequest request) {
        JwtUser user = decode(request);
        if (!Objects.equals(user.getId(), userId) && !user.isAdministrator()) {
            throw new NotAuthorizedException("Not authorized!");
        }
    }



    public JwtUser decode(HttpServletRequest request) {
        var token = request.getHeader(HEADER_STRING);
        if (token == null) {
            return null;
        }

        var claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token.substring(7))
                .getBody();

        return JwtUser.builder()
                .id(Integer.parseInt(claims.getId()))
                .username(claims.getSubject())
                .isAdministrator((boolean) claims.get("isAdministrator"))
                .build();
    }


    public String encodeJwt(JwtUser user) {
        LocalDateTime date = LocalDateTime.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS);

        JwtBuilder builder = Jwts.builder().setId(user.getId().toString())
                .setSubject(user.getId().toString())
                .setIssuer("userjwtauthwebservice")
                .claim("username", user.getUsername())
                .claim("isAdministrator", user.isAdministrator())
                .setExpiration(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, key);

        return builder.compact();
    }
}
