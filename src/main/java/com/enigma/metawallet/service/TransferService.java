package com.enigma.metawallet.service;

import com.enigma.metawallet.entity.Transfer;
import com.enigma.metawallet.model.request.TransferRequest;
import com.enigma.metawallet.model.response.TransferResponse;

import java.util.List;

public interface TransferService {

    TransferResponse transferWithUserId(TransferRequest transferRequest);
    List<Transfer> getAllTransferHistoryByUserId(String userId);
    List<Transfer> getAllInTransferHistoryByUserId(String userId);
    List<Transfer> getAllOutTransferHistoryByUserId(String userId);

}
