package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.model.request.AuthRequest;
import com.enigma.metawallet.model.request.UserRegisterRequest;
import com.enigma.metawallet.model.response.AdminRegisterResponse;
import com.enigma.metawallet.model.response.LoginResponse;
import com.enigma.metawallet.model.response.UserRegisterResponse;
import com.enigma.metawallet.repository.UserCredentialRepository;
import com.enigma.metawallet.security.BCryptUtils;
import com.enigma.metawallet.security.JwtUtils;
import com.enigma.metawallet.service.AuthService;
import com.enigma.metawallet.service.RoleService;
import com.enigma.metawallet.service.WalletService;
import com.enigma.metawallet.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptUtils bCryptUtils;
    private final WalletService walletService;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final ValidationUtil validationUtil;

    @Override
    public UserRegisterResponse userRegister(UserRegisterRequest request) {
        return null;
    }

    @Override
    public AdminRegisterResponse adminRegister(AuthRequest request) {
        return null;
    }

    @Override
    public LoginResponse login(AuthRequest request) {
        return null;
    }
}
