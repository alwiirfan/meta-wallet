package com.enigma.metawallet.controller;

import com.enigma.metawallet.model.request.ChangePasswordRequest;
import com.enigma.metawallet.model.request.UserRequest;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.CommonResponse;
import com.enigma.metawallet.model.response.PagingResponse;
import com.enigma.metawallet.model.response.UserResponse;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUserForAdmin(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){

        Page<UserResponse> userResponses = userService.getAllUserForAdmin(size ,page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<UserResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get all users")
                        .data(userResponses.getContent())
                        .paging(PagingResponse.builder()
                                .page(userResponses.getNumberOfElements())
                                .count(userResponses.getNumber())
                                .size(userResponses.getSize())
                                .totalPages(userResponses.getTotalPages())
                                .build())
                        .build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getUserById(@PathVariable String id){
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<UserResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get user by id with name : " + userResponse.getName())
                        .data(userResponse)
                        .build());
    }

    @PatchMapping(path = "/wallet")
    public ResponseEntity<?> topUpWallet(@Valid @RequestBody WalletRequest request){
        WalletResponse walletResponse = userService.topUpWallet(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<WalletResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully top up with Id : " + walletResponse.getUserId())
                        .data(walletResponse)
                        .build());
    }

    @GetMapping(path = "/{id}/wallet")
    public ResponseEntity<?> getWalletByUserId(@PathVariable String id){
        WalletResponse walletResponse = userService.getWalletByUserId(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<WalletResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get wallet by user Id")
                        .data(walletResponse)
                        .build());
    }

    @PutMapping
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserRequest request){
        UserResponse userResponse = userService.updateUser(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<UserResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully to update user with name : " + userResponse.getName())
                        .data(userResponse)
                        .build());
    }

    @PatchMapping(path = "/{userId}")
    public ResponseEntity<?> changePasswordUser(@PathVariable String userId, @Valid @RequestBody ChangePasswordRequest request){
        userService.changePassword(userId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successful to change password your account")
                        .build());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUserByUserId(@PathVariable String id){
        userService.deleteUserById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully delete user with Id : " + id)
                        .build());
    }

}
