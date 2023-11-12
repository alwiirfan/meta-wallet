package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.*;
import com.enigma.metawallet.entity.roleContract.ERole;
import com.enigma.metawallet.model.request.AuthRequest;
import com.enigma.metawallet.model.request.UserRegisterRequest;
import com.enigma.metawallet.model.response.*;
import com.enigma.metawallet.repository.UserCredentialRepository;
import com.enigma.metawallet.security.BCryptUtils;
import com.enigma.metawallet.security.JwtUtils;
import com.enigma.metawallet.service.*;
import com.enigma.metawallet.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserCredentialRepository userCredentialRepository;
    private final AuthenticationManager authenticationManager;
    private final BCryptUtils bCryptUtils;
    private final WalletService walletService;
    private final RoleService roleService;
    private final JwtUtils jwtUtils;
    private final ValidationUtil validationUtil;
    private final UserService userService;
    private final AdminService adminService;

    @Override
    public RegisterResponse userRegister(UserRegisterRequest request) {
        validationUtil.validate(request);
        try {
            Role role = roleService.getOrSave(ERole.ROLE_USER);
            UserCredential userCredential = UserCredential.builder()
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Wallet wallet = Wallet.builder()
                    .balance(0L)
                    .build();
            walletService.create(wallet);

            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .city(request.getCity())
                    .country(request.getCountry())
                    .mobilePhone(request.getMobilePhone())
                    .userCredential(userCredential)
                    .wallet(wallet)
                    .dateOfBirth(LocalDate.parse(request.getDateOfBirth(), DateTimeFormatter.ofPattern("dd-MM-yyyy")))
                    .build();
            userService.create(user);

            return RegisterResponse.builder()
                    .email(user.getEmail())
                    .username(userCredential.getUsername())
                    .build();

        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }

    }

    @Override
    public RegisterResponse adminRegister(AuthRequest request) {
        validationUtil.validate(request);
        try {
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);
            UserCredential userCredential = UserCredential.builder()
                    .email(request.getEmail())
                    .username(request.getUsername())
                    .password(bCryptUtils.hashPassword(request.getPassword()))
                    .roles(List.of(role))
                    .build();
            userCredentialRepository.saveAndFlush(userCredential);

            Wallet wallet = Wallet.builder()
                    .balance(0L)
                    .build();
            walletService.create(wallet);

            Admin admin = Admin.builder()
                    .wallet(wallet)
                    .userCredential(userCredential)
                    .name(request.getName())
                    .email(request.getEmail())
                    .build();
            adminService.create(admin);

            return RegisterResponse.builder()
                    .email(admin.getEmail())
                    .username(userCredential.getUsername())
                    .build();

        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Admin already exist");
        }

    }

    @Override
    public LoginResponse login(AuthRequest request) {
        validationUtil.validate(request);
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        List<String> roles = userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        String token = jwtUtils.generateToken(userDetail.getEmail());
        return LoginResponse.builder()
                .email(userDetail.getEmail())
                .roles(roles)
                .token(token)
                .build();
    }

    @Override
    public void logout(String token) {
        validationUtil.validate(token);

        try {
            String email = jwtUtils.getEmailByToken(token);
            jwtUtils.addToBlacklist(token);
            log.info("Successful logout from the account with email: {}", email);

            boolean tokenBlacklisted = jwtUtils.isTokenBlacklisted(token);
            if (tokenBlacklisted){
                log.info("Successful logout from your account");
            }else {
                log.error("Token is not valid");
            }
        }catch (RuntimeException e){
            log.error("Error during logout: {}", e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or expired token");
        }

    }
}
