package com.enigma.metawallet.service;

import com.enigma.metawallet.model.response.TransferResponse;

import java.util.List;

public interface TransferService {

    List<TransferResponse> getAllTransferByUserId(String id);
    TransferResponse transferToUser();

}
