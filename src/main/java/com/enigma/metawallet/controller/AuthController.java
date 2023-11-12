package com.enigma.metawallet.controller;

import com.enigma.metawallet.model.request.AuthRequest;
import com.enigma.metawallet.model.request.UserRegisterRequest;
import com.enigma.metawallet.model.response.CommonResponse;
import com.enigma.metawallet.model.response.LoginResponse;
import com.enigma.metawallet.model.response.RegisterResponse;
import com.enigma.metawallet.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request){
        RegisterResponse registerResponse = authService.userRegister(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<RegisterResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully registered user with username : " + registerResponse.getUsername())
                        .data(registerResponse)
                        .build());
    }

    @PostMapping(path = "/register/admin")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AuthRequest request){
        RegisterResponse registerResponse = authService.adminRegister(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<RegisterResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("Successfully registered admin with username : " + registerResponse.getUsername())
                        .data(registerResponse)
                        .build());
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequest request){
        LoginResponse loginResponse = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<LoginResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success login with email : " + loginResponse.getEmail())
                        .data(loginResponse)
                        .build());
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = HttpHeaders.AUTHORIZATION) String token){
        String cleanedToken = cleanToken(token);
        authService.logout(cleanedToken);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<String>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successful logout from your account")
                        .build());
    }

    private String cleanToken(String token) {
        return token.replace("Bearer ", ""); // Hapus "Bearer " dari awal token jika ada
    }
}
