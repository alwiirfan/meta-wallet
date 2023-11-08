package com.enigma.metawallet.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BCryptUtils {

    private final PasswordEncoder passwordEncoder;

    public String hashPassword(String password){
        return passwordEncoder.encode(password);
    }

}
