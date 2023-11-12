package com.enigma.metawallet.service;

import com.enigma.metawallet.model.request.AuthRequest;
import com.enigma.metawallet.model.request.UserRegisterRequest;
import com.enigma.metawallet.model.response.LoginResponse;
import com.enigma.metawallet.model.response.RegisterResponse;

public interface AuthService {

    RegisterResponse userRegister(UserRegisterRequest request);
    RegisterResponse adminRegister(AuthRequest request);
    LoginResponse login(AuthRequest request);
    void logout(String token);

}
