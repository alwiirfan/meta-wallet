package com.enigma.metawallet.service;

import com.enigma.metawallet.model.request.WalletRequest;
import com.enigma.metawallet.model.response.WalletResponse;

public interface WalletService {

    WalletResponse topUpToWallet(WalletRequest request);
    WalletResponse getWalletById(String id);

}
