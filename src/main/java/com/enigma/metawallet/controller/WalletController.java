package com.enigma.metawallet.controller;

import com.enigma.metawallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;



}
