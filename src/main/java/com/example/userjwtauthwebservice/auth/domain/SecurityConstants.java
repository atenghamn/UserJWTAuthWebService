package com.example.userjwtauthwebservice.auth.domain;

public interface SecurityConstants {
    String SECRET ="GucEznMcUPCp2wFU.7PC2KQ!s349-HG8rxMTC9843fEFwU8d2ryAWnrg7pjKgfbXhNzT2ax4ibw48mXFs@*tHEf2Wuz@mGt7WRvP";
    long EXPIRATION_TIME = 864_000_00; // En dag
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";

}
