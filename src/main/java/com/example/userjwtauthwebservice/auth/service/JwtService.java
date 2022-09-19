package com.example.userjwtauthwebservice.auth.service;

import com.example.userjwtauthwebservice.auth.domain.JwtUser;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static com.example.userjwtauthwebservice.auth.domain.SecurityConstants.*;

@Service
public class JwtService {
    private static final Key key = new SecretKeySpec(
            DatatypeConverter.parseBase64Binary(SECRET),
            SignatureAlgorithm.HS512.getJcaName());

    public String encodeJwt(JwtUser user) {
        LocalDateTime date = LocalDateTime.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS);

        JwtBuilder builder = Jwts.builder().setId(user.getId().toString())
                .setSubject(user.getUsername())
                .setIssuer("userjwtauthwebservice")
                .claim("isAdministrator", user.getIsAdministrator())
                .setExpiration(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, key);

        return builder.compact();
    }
}
