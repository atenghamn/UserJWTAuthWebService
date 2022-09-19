package com.example.userjwtauthwebservice.commons;

import com.example.userjwtauthwebservice.auth.domain.TokenWrapper;
import com.example.userjwtauthwebservice.exception.NotAuthorizedException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorizationUtil {
    private static final String AUTORIZED_ACCESS_LOG = "Authorized access {} by user: {}!";

    private AuthorizationUtil() {
        // Util class
        // Util class
    }

    public static void authorizeAdmin(String bearer, String actionDescription) {
        var auth = new TokenWrapper(bearer);
        if (!auth.isAdministrator()) {
            throw new NotAuthorizedException("You need to be administrator to " + actionDescription);
        } else {
            log.info(AUTORIZED_ACCESS_LOG, actionDescription, auth.getUserId());
        }
    }

    public static boolean authorizeAdminForDataManipulation(String bearer, String actionDescription) {
        var auth = new TokenWrapper(bearer);
        if (!auth.isAdministrator())
            return false;

        log.info(AUTORIZED_ACCESS_LOG, actionDescription, auth.getUserId());
        return true;
    }

}
