package com.example.userjwtauthwebservice.commons;

import com.example.userjwtauthwebservice.auth.domain.JwtUser;
import com.example.userjwtauthwebservice.auth.service.JwtService;
import com.example.userjwtauthwebservice.exception.NotAuthorizedException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static com.example.userjwtauthwebservice.commons.AuthorizationUtil.authorizeAdmin;
import static com.example.userjwtauthwebservice.commons.AuthorizationUtil.authorizeAdminForDataManipulation;
import static org.junit.jupiter.api.Assertions.*;

public class AuthorizationUtilTest {

    private static String ADMIN_TOKEN;
    private static String USER_TOKEN;

    @BeforeAll
    public static void setup(){
        var jwtService = new JwtService();

        ADMIN_TOKEN ="Bearer " +  jwtService.encodeJwt(JwtUser.builder()
                    .id(1)
                    .username("Admin")
                    .isAdministrator(true)
                .build());

        USER_TOKEN = "Bearer " + jwtService.encodeJwt(JwtUser.builder()
                        .id(2)
                        .username("Not Admin")
                        .isAdministrator(false)
                .build());
    }

    @Test
    void whenAdmin_thenAuthorize() {
        assertDoesNotThrow(() -> authorizeAdmin(ADMIN_TOKEN, "Authorize admin with admin token"));
    }

    @Test
    void whenNotAdmin_thenDontAuthorize(){
        assertThrows(NotAuthorizedException.class, () -> authorizeAdmin(USER_TOKEN, "Dont authorize admin with non-admin token"));
    }

    @Test
    void whenAdmin_thenReturnTrue(){
        assertTrue(authorizeAdminForDataManipulation(ADMIN_TOKEN, "Authorize admin with admin token"));
    }

    @Test
    void whenNotAdmin_thenReturnFalse(){
        assertFalse(authorizeAdminForDataManipulation(USER_TOKEN, "Dont authorize admin with non-admin token"));
    }
}
