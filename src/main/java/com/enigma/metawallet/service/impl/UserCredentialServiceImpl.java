package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.repository.UserCredentialRepository;
import com.enigma.metawallet.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserCredentialServiceImpl implements UserCredentialService {


    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserCredential create(UserCredential userCredential) {
        return userCredentialRepository.save(userCredential);
    }

}
