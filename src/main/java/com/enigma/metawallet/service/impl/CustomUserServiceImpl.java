package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.repository.UserCredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomUserServiceImpl implements UserDetailsService {

    private final UserCredentialRepository userCredentialRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserCredential userCredential = userCredentialRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<SimpleGrantedAuthority> grantedAuthorities = userCredential.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRole().name())).collect(Collectors.toList());

        return UserDetailImpl.builder()
                .email(userCredential.getEmail())
                .password(userCredential.getPassword())
                .authorities(grantedAuthorities)
                .build();
    }
}
