package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.repository.WalletRepository;
import com.enigma.metawallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public WalletResponse topUpToWallet(WalletRequest request) {
        return null;
    }

    @Override
    public WalletResponse getWalletById(String id) {
        return null;
    }
}
