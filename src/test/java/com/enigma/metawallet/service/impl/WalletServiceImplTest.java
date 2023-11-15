package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.repository.WalletRepository;
import com.enigma.metawallet.service.WalletService;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class WalletServiceImplTest {

    private final WalletRepository walletRepository = mock(WalletRepository.class);
    private final WalletService walletService = new WalletServiceImpl(walletRepository);

    @Test
    void create() {
        Wallet wallet = new Wallet();
        wallet.setBalance(1000l);

        when(walletRepository.save(any(Wallet.class))).thenReturn(wallet);

        Wallet createWallet = walletService.create(wallet);

        verify(walletRepository, times(1)).save(wallet);

        assertEquals(1000l, createWallet.getBalance());
    }

    @Test
    void update() {


        String walletId = "iniwallet";


        Wallet oldWallet = new Wallet(" ", 1000L, new User(), new Admin());
        Wallet newWallet = new Wallet("", 1000L, new User(), new Admin());

        when(walletRepository.findById(walletId)).thenReturn(Optional.of(oldWallet));
        when(walletRepository.save(newWallet)).thenReturn(newWallet);

        walletService.update(newWallet);

        verify(walletRepository, times(1)).findById(walletId);
        verify(walletRepository, times(1)).save(newWallet);

        ;
    }
    }
