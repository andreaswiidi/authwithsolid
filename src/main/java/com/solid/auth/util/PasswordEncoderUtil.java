package com.solid.auth.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderUtil {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    public static String toHash(String value) {
        return bCryptPasswordEncoder.encode(value);
    }
}