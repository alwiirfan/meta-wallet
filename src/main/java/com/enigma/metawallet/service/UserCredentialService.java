package com.enigma.metawallet.service;

import com.enigma.metawallet.entity.UserCredential;

public interface UserCredentialService {

    UserCredential create(UserCredential userCredential);
    void update(UserCredential userCredential);

}
