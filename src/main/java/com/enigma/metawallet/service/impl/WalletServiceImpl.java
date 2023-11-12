package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.repository.WalletRepository;
import com.enigma.metawallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;

    @Override
    public Wallet create(Wallet request) {
        return walletRepository.save(request);
    }

    @Override
    public void update(Wallet wallet) {

        if (wallet.getId() == null){ // pastikan wallet id tidak null
            throw new IllegalArgumentException("Wallet Id cannot be null");
        }

        Wallet currentWallet = walletRepository.findById(wallet.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet is not found"));

        currentWallet.setBalance(wallet.getBalance());
        walletRepository.save(currentWallet);
    }

}
