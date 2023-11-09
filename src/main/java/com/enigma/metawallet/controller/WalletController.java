package com.enigma.metawallet.controller;

import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.CommonResponse;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    @PatchMapping(path = "/topUp")
    public ResponseEntity<?> topUpUser(@Valid @RequestBody WalletRequest request){
        WalletResponse walletResponse = walletService.topUpToWallet(request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<WalletResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully topUp with ID : " + walletResponse.getId())
                        .data(walletResponse)
                        .build());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getWalletById(@PathVariable String id){
        WalletResponse walletResponse = walletService.getWalletById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<WalletResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successfully get wallet by ID : " + walletResponse.getId())
                        .data(walletResponse)
                        .build());
    }

}
