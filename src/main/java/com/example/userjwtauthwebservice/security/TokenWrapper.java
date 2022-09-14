package com.example.userjwtauthwebservice.security;

import com.example.userjwtauthwebservice.commons.Strings;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class TokenWrapper {

    private Jws<Claims> claims;

    public TokenWrapper(String token){
        if(!Strings.isNullOrEmpty(token) && Strings.isNullOrEmpty(SecurityConstants.SECRET)){
            var tokenWithoutPrefix = token.substring(7);
            this.claims = Jwts.parser().setSigningKey(SecurityConstants.SECRET).parseClaimsJws(tokenWithoutPrefix);
        }
    }
    public  Integer getUserid(){return Integer.parseInt(claims.getBody().getSubject());}
    public boolean isAdministrator() {return (Boolean) claims.getBody().get("isAdministrator");}
}
