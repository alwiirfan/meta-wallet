package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Role;
import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.entity.roleContract.ERole;
import com.enigma.metawallet.model.request.AuthRequest;
import com.enigma.metawallet.model.request.UserRegisterRequest;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.AdminRegisterResponse;
import com.enigma.metawallet.model.response.LoginResponse;
import com.enigma.metawallet.model.response.UserRegisterResponse;
import com.enigma.metawallet.repository.UserCredentialRepository;
import com.enigma.metawallet.security.BCryptUtils;
import com.enigma.metawallet.security.JwtUtils;
import com.enigma.metawallet.service.AuthService;
import com.enigma.metawallet.service.RoleService;
import com.enigma.metawallet.service.UserService;
import com.enigma.metawallet.service.WalletService;
import com.enigma.metawallet.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
    private final UserService userService;

    @Override
    public UserRegisterResponse userRegister(UserRegisterRequest request) {
        validationUtil.validate(request);
        try {
            Role role = roleService.getOrSave(ERole.USER);
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            SimpleDateFormat dob = new SimpleDateFormat();

//            User user = User.builder()
//                    .dateOfBirth(new Date(dob.parse(request.get)))
//                    .build();
//            walletService.createNewWallet(walletRequest);

        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }


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
