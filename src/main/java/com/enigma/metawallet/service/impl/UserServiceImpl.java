package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.model.request.UserRequest;
import com.enigma.metawallet.model.response.UserResponse;
import com.enigma.metawallet.repository.UserRepository;
import com.enigma.metawallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserResponse createNewUser(UserRequest request) {

        UserCredential userCredential = UserCredential.builder().build();

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .address(request.getAddress())
                .country(request.getCountry())
                .city(request.getCity())
                .mobilePhone(request.getMobilePhone())
                .userCredential(userCredential)
                .build();
        userRepository.save(user);
        return UserResponse.builder()
                .username(userCredential.getUsername())
                .build();
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
