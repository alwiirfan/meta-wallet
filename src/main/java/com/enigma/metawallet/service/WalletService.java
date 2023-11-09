package com.enigma.metawallet.service;

import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.WalletResponse;

public interface WalletService {

    Wallet createNewWallet(Wallet request);
    WalletResponse topUpToWallet(WalletRequest request);
    WalletResponse getWalletById(String id);

}
