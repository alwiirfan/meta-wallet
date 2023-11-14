package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.repository.UserCredentialRepository;
import com.enigma.metawallet.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {


    private final UserCredentialRepository userCredentialRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserCredential create(UserCredential userCredential) {
        return userCredentialRepository.save(userCredential);
    }

    @Override
    public void update(UserCredential userCredential) {
        UserCredential currentUserCredential = userCredentialRepository.findById(userCredential.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User credential is not found"));

        currentUserCredential.setPassword(passwordEncoder.encode(userCredential.getPassword()));
        userCredentialRepository.save(userCredential);
    }

}
