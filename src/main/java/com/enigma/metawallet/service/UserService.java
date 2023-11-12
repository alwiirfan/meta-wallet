package com.enigma.metawallet.service;

import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.model.request.ChangePasswordRequest;
import com.enigma.metawallet.model.request.UserRequest;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.UserResponse;
import com.enigma.metawallet.model.response.WalletResponse;
import org.springframework.data.domain.Page;


public interface UserService {

    User create(User request);
    Page<UserResponse> getAllUserForAdmin(Integer size, Integer page);
    UserResponse getUserById(String id);
    WalletResponse getWalletByUserId(String id);
    WalletResponse topUpWallet(WalletRequest request);
    UserResponse updateUser(UserRequest request);
    void changePassword(String userId, ChangePasswordRequest request);
    void deleteUserById(String id);

}