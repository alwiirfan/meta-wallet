package com.enigma.metawallet.service;

import com.enigma.metawallet.model.request.UserRequest;
import com.enigma.metawallet.model.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createNewUser(UserRequest request);
    List<UserResponse> getAllUserForAdmin();
    UserResponse getUserById(String id);
    UserResponse updateUser(UserRequest request);
    String deleteUserById(String id);

}