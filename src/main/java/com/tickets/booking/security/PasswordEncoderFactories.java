package com.tickets.booking.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

public class PasswordEncoderFactories {

    private PasswordEncoderFactories() {
    }

    public static PasswordEncoder createDelegatingPasswordEncoder() {
        String encodingId = "bcrypt10";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(encodingId, new BCryptPasswordEncoder(10));
        encoders.put("bcrypt", new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(encodingId, encoders);
    }

}
