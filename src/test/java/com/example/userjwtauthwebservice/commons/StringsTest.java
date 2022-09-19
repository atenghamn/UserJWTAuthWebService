package com.example.userjwtauthwebservice.commons;

import org.junit.jupiter.api.Test;
import static com.example.userjwtauthwebservice.commons.Strings.isNotNullOrEmpty;
import static com.example.userjwtauthwebservice.commons.Strings.isNullOrEmpty;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StringsTest {

  public static String NOT_EMPTY_STRING = "ABCD";
  public static String EMPTY_STRING = "";

    @Test
    public void notEmptyString_ReturnTrue(){
        assertTrue(isNotNullOrEmpty(NOT_EMPTY_STRING));
    }

    @Test
    public void emptyString_ReturnFalse(){
        assertFalse(isNotNullOrEmpty(EMPTY_STRING));
    }

    @Test
    public void notEmptyString_ReturnFalse(){
        assertFalse(isNullOrEmpty(NOT_EMPTY_STRING));
    }

    @Test
    public void emptyString(){
        assertTrue(isNullOrEmpty(EMPTY_STRING));
    }
}
