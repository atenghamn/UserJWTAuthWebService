package com.example.userjwtauthwebservice.commons;

public interface Strings {

    /**
     * Returns true if the string is not null, empty or only contains blanks.
     *
     * @param string The string to test.
     * @return false if the String contains a value that does not consist of blanks.
     */
    static boolean isNotNullOrEmpty(String string) {
        return !(string == null || string.isBlank());
    }

    /**
     * Returns true if the string is null, empty or only contains blanks.
     *
     * @param string The string to test.
     * @return false if the String does not contain a value or that if it consist of blanks (spaces).
     */
    static boolean isNullOrEmpty(String string) {
        return (string == null || string.isBlank());
    }
}