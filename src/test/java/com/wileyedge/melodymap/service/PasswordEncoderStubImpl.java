package com.wileyedge.melodymap.service;

import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderStubImpl implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        // Return the raw password
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        // Passwords match if they are equal
        return rawPassword.toString().equals(encodedPassword);
    }

    @Override
    public boolean upgradeEncoding(String encodedPassword) {
        // Return false since upgrading encoding is not implemented in this stub
        return false;
    }
}
