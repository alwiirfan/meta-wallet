package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.repository.WalletRepository;
import com.enigma.metawallet.service.WalletService;
import com.enigma.metawallet.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ValidationUtil validationUtil;

    @Override
    public WalletResponse createNewWallet(WalletRequest request) {
        validationUtil.validate(request);

        UserCredential userCredential = UserCredential.builder().build();

        Wallet wallet = Wallet.builder()
                .balance(null)
                .userCredential(userCredential)
                .build();
        walletRepository.save(wallet);

        return WalletResponse.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }

    @Override
    public WalletResponse topUpToWallet(WalletRequest request) {
        return null;
    }

    @Override
    public WalletResponse getWalletById(String id) {
        return null;
    }
}
