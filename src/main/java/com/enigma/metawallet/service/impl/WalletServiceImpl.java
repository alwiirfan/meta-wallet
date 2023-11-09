package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.WalletResponse;
import com.enigma.metawallet.repository.WalletRepository;
import com.enigma.metawallet.service.WalletService;
import com.enigma.metawallet.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository walletRepository;
    private final ValidationUtil validationUtil;

    @Override
    public Wallet create(Wallet request) {
        validationUtil.validate(request);
        return walletRepository.save(request);
    }

    @Override
    public WalletResponse topUpToWallet(WalletRequest request) {
        validationUtil.validate(request);

        Optional<Wallet> wallet = walletRepository.findById(request.getId());
        if (wallet.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet is not found");
        }

        Wallet build = Wallet.builder()
                .id(wallet.get().getId())
                .balance(request.getBalance())
                .build();
        walletRepository.saveAndFlush(build);

        return WalletResponse.builder()
                .id(build.getId())
                .balance(build.getBalance())
                .build();
    }

    @Override
    public WalletResponse getWalletById(String id) {
        validationUtil.validate(id);
        Wallet wallet = walletRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet is not found"));

        return WalletResponse.builder()
                .id(wallet.getId())
                .balance(wallet.getBalance())
                .build();
    }
}
