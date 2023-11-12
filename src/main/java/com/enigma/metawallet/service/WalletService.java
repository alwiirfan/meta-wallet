package com.enigma.metawallet.service;

import com.enigma.metawallet.entity.Wallet;

public interface WalletService {

    Wallet create(Wallet request);
    void update(Wallet wallet);

}
