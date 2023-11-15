package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.entity.Transfer;
import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.request.TransferRequest;
import com.enigma.metawallet.model.response.TransferResponse;
import com.enigma.metawallet.repository.AdminRepository;
import com.enigma.metawallet.repository.TransferRepository;
import com.enigma.metawallet.repository.UserRepository;
import com.enigma.metawallet.repository.WalletRepository;
import com.enigma.metawallet.service.TransferService;
import com.enigma.metawallet.util.TransferValidation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransferServiceImplTest {

    private final TransferRepository transferRepository=mock(TransferRepository.class);
    private final UserRepository userRepository =mock(UserRepository.class);
    private final WalletRepository walletRepository = mock(WalletRepository.class);
    private final AdminRepository adminRepository = mock(AdminRepository.class);
    private final TransferValidation transferValidation = mock(TransferValidation.class);
    private final TransferService transferService = new TransferServiceImpl(transferRepository, userRepository, walletRepository, adminRepository, transferValidation);


    @Test
    void successfullTransferWithUserIdToOtherUserId() {
        User fromUserId = User.builder().id("fromUserId").wallet(Wallet.builder().id("1").balance(1000000L).build()).build();
        User toUserId = User.builder().id("toUserIdUserId").wallet(Wallet.builder().id("2").balance(1000000L).build()).build();
        Admin admin = Admin.builder().id(1L).wallet(Wallet.builder().id("3").balance(0L).build()).build();
        TransferRequest transferRequest = TransferRequest.builder()
                .fromUserId("fromUserId")
                .toUserId("toUserId")
                .nominalTransfer(50000L)
                .build();
        when(userRepository.findWalletByUserId("fromUserId")).thenReturn(fromUserId.getWallet());
        when(userRepository.findWalletByUserId("toUserId")).thenReturn(toUserId.getWallet());
        when(adminRepository.findWalletByAdminId(any(Long.class))).thenReturn(admin.getWallet());
        when(transferRepository.countByFromUserIdAndTransferOutIsNotNullAndTransDateAfter(fromUserId.getId(), LocalDateTime.now().minusHours(24))).thenReturn(1L);
        when(transferValidation.checkAccess("fromUserId")).thenReturn(true);

        TransferResponse transferServiceActual = transferService.transferWithUserId(transferRequest);

        verify(userRepository, times(1)).findWalletByUserId("fromUserId");
        verify(userRepository, times(1)).findWalletByUserId("toUserId");
        verify(adminRepository, times(1)).findWalletByAdminId(any(Long.class));

        assertEquals(948500, fromUserId.getWallet().getBalance());
        assertEquals(1050000, toUserId.getWallet().getBalance());
        assertEquals(1500, admin.getWallet().getBalance());
    }


    @Test
    void getAllTransferHistoryByUserId() {
        User user = User.builder().id("fromUserId").build();

        Transfer transferin = Transfer.builder().transferIn(50000L).toUserId("fromUserId").build();
        Transfer transferout = Transfer.builder().transferOut(50000L).fromUserId("fromUserId").build();
        List<Transfer> transfer = List.of(transferout, transferin);
        when(userRepository.findById("fromUserId")).thenReturn(Optional.of(user));
        when(transferRepository.findAllTransfersByUserIdWithTransfer("fromUserId")).thenReturn(transfer);
        when(transferValidation.checkAccess("fromUserId")).thenReturn(true);

        List<Transfer> transfers = transferService.getAllTransferHistoryByUserId("fromUserId");

        verify(transferRepository, times(1)).findAllTransfersByUserIdWithTransfer("fromUserId");

        assertEquals(2, transfers.size());
    }

    @Test
    void getAllOutTransferHistoryByUserId() {
        User user = User.builder().id("fromUserId").build();
        Transfer transferout = Transfer.builder().transferOut(50000L).fromUserId("fromUserId").build();
        when(userRepository.findById("fromUserId")).thenReturn(Optional.of(user));
        when(transferRepository.findAllByFromUserIdAndTransferOutIsNotNull("fromUserId")).thenReturn(Collections.singletonList(transferout));
        when(transferValidation.checkAccess("fromUserId")).thenReturn(true);

        List<Transfer> transfers = transferService.getAllOutTransferHistoryByUserId("fromUserId");

        verify(transferRepository, times(1)).findAllByFromUserIdAndTransferOutIsNotNull("fromUserId");

        assertEquals(1, transfers.size());
    }

    @Test
    void getAllInTransferHistoryByUserId() {
        User user = User.builder().id("toUserId").build();
        Transfer transferin = Transfer.builder().transferIn(50000L).fromUserId("toUserId").build();
        when(userRepository.findById("toUserId")).thenReturn(Optional.of(user));
        when(transferRepository.findAllByToUserIdAndTransferInIsNotNull("toUserId")).thenReturn(Collections.singletonList(transferin));
        when(transferValidation.checkAccess("toUserId")).thenReturn(true);

        List<Transfer> transfers = transferService.getAllInTransferHistoryByUserId("toUserId");

        verify(transferRepository, times(1)).findAllByToUserIdAndTransferInIsNotNull("toUserId");

        assertEquals(1, transfers.size());
    }
}