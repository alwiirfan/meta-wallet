package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.repository.UserCredentialRepository;
import com.enigma.metawallet.service.UserCredentialService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserCredentialServiceImplTest {
    private final UserCredentialRepository userCredentialRepository = mock(UserCredentialRepository.class);

    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserCredentialService userCredentialService = new UserCredentialServiceImpl(userCredentialRepository, passwordEncoder);
    @Test
    void create() {
        UserCredential userCredential = new UserCredential();
        userCredential.setId("");
        userCredential.setPassword("");
        userCredential.setEmail("");
        userCredential.setUsername("");

        when(userCredentialRepository.save(any(UserCredential.class))).thenReturn(userCredential);

        UserCredential createUserCredential = userCredentialService.create(userCredential);

        verify(userCredentialRepository, times(1)).save(userCredential);

        assertEquals("", createUserCredential.getId());
        assertEquals("", createUserCredential.getPassword());
        assertEquals("", createUserCredential.getEmail());
        assertEquals("", createUserCredential.getUsername());
    }
}