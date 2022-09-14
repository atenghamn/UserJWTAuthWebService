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

    /**
     * Check if the logged in user is the same as the person tries to access the endpoint or if the user is an
     * administrator.
     *
     * @param userId the userId as an Integer.
     * @param request Servlet Request.
     * @throws NotAuthorizedException Thrown if the user is not the same.
     */
    public void isAdministrator(Integer userId, HttpServletRequest request) {
        JwtUser user = decode(request);
        if (!Objects.equals(user.getId(), userId) && !user.isAdministrator()) {
            throw new NotAuthorizedException("Not authorized!");
        }
    }


    /**
     * Decodes a Json Web Token and return an user object.
     *
     * @param request that contains the "Authorization Bearer" token.
     * @return A JwtUser if the user is logged in or null.
     */
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

    /**
     * Create a new Json Web Token from account and user objects.
     *
     * @param user An existing user.
     * @return Json Web Token as String.
     */
    public String encodeJwt(JwtUser user) {
        LocalDateTime date = LocalDateTime.now().plus(EXPIRATION_TIME, ChronoUnit.MILLIS);

        JwtBuilder builder = Jwts.builder().setId(user.getId().toString())
                .setSubject(user.getId().toString())
                .setIssuer("localhost:8080")
                .claim("isAdministrator", user.isAdministrator())
                .setExpiration(Date.from(date.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, key);

        return builder.compact();
    }
}
