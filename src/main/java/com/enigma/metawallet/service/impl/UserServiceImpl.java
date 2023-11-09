package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.model.request.UserRequest;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.UserResponse;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.repository.UserRepository;
import com.enigma.metawallet.service.UserService;
import com.enigma.metawallet.service.WalletService;
import com.enigma.metawallet.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ValidationUtil validationUtil;

    @Override
    public User createNewUser(User request) {
        validationUtil.validate(request);

        try {
            User user = User.builder()
                    .name(request.getName())
                    .email(request.getEmail())
                    .address(request.getAddress())
                    .country(request.getCountry())
                    .city(request.getCity())
                    .mobilePhone(request.getMobilePhone())
                    .build();
            userRepository.save(user);

            return user;
        }catch (DataIntegrityViolationException e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }

    }

    @Override
    public List<UserResponse> getAllUserForAdmin() {
        return null;
    }

    @Override
    public UserResponse getUserById(String id) {
        return null;
    }

    @Override
    public UserResponse updateUser(UserRequest request) {
        return null;
    }

    @Override
    public String deleteUserById(String id) {
        return null;
    }
}
