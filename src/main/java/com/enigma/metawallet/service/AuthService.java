package com.enigma.metawallet.service;

import com.enigma.metawallet.model.request.AuthRequest;
import com.enigma.metawallet.model.request.UserRegisterRequest;
import com.enigma.metawallet.model.response.AdminRegisterResponse;
import com.enigma.metawallet.model.response.LoginResponse;
import com.enigma.metawallet.model.response.UserRegisterResponse;

public interface AuthService {

    UserRegisterResponse userRegister(UserRegisterRequest request);
    AdminRegisterResponse adminRegister(AuthRequest request);
    LoginResponse login(AuthRequest request);

}
