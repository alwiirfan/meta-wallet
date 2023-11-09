package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.model.response.TransferResponse;
import com.enigma.metawallet.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    @Override
    public List<TransferResponse> getAllTransferByUserId(String id) {
        return null;
    }

    @Override
    public TransferResponse transferToUser() {
        return null;
    }
}
