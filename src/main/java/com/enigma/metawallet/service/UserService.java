package com.enigma.metawallet.service;

import com.enigma.metawallet.model.request.UserRequest;
import com.enigma.metawallet.model.response.CommonResponse;
import com.enigma.metawallet.model.response.UserResponse;

import java.util.List;

public interface UserService {

    CommonResponse<List<UserResponse>> getAllUserForAdmin();
    CommonResponse<UserResponse> getUserById(String id);
    CommonResponse<UserResponse> updateUser(UserRequest request);
    CommonResponse<String> deleteUserById(String id);

}