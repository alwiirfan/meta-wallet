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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final UserRepository userRepository;
    private final WalletRepository walletRepository;
    private final AdminRepository adminRepository; //pembantu

    //    @PreAuthorize("#userId == authentication.principal.id")
    @Override
    @Transactional
    public TransferResponse transferWithUserId(TransferRequest transferRequest) {
        Wallet fromUserWallet = userRepository.findWalletByUserId(transferRequest.getFromUserId());
        Wallet toUserWallet = userRepository.findWalletByUserId(transferRequest.getToUserId());
        Wallet walletAdmin = adminRepository.findWalletByAdminId(1L);
        Long totalTransfer24hours = transferRepository.countByFromUserIdAndTransferOutIsNotNullAndTransDateAfter(transferRequest.getFromUserId(), LocalDateTime.now().minusHours(24));
        if ( fromUserWallet != null &&  toUserWallet != null && fromUserWallet.getBalance()>50000 &&
                transferRequest.getNominalTransfer()<25000000&&transferRequest.getNominalTransfer()>0 && totalTransfer24hours<=1000) {
            if (fromUserWallet.getBalance() - transferRequest.getNominalTransfer() > 15000) {

                fromUserWallet.setBalance(fromUserWallet.getBalance() - transferRequest.getNominalTransfer() - 1500);
                walletRepository.saveAndFlush(fromUserWallet);

                toUserWallet.setBalance(toUserWallet.getBalance() + transferRequest.getNominalTransfer());
                walletRepository.saveAndFlush(toUserWallet);

                Transfer from = Transfer.builder()
                        .transferOut(transferRequest.getNominalTransfer())
                        .transferIn(null)
                        .fromUserId(transferRequest.getFromUserId())
                        .toUserId(transferRequest.getToUserId())
                        .transDate(LocalDateTime.now())
                        .tax(1500L)
                        .build();
                transferRepository.saveAndFlush(from);

                Transfer to = Transfer.builder()
                        .transferOut(null)
                        .transferIn(transferRequest.getNominalTransfer())
                        .fromUserId(transferRequest.getFromUserId())
                        .toUserId(transferRequest.getToUserId())
                        .transDate(LocalDateTime.now())
                        .build();
                transferRepository.saveAndFlush(to);

                walletAdmin.setBalance(walletAdmin.getBalance()+1500);
                walletRepository.saveAndFlush(walletAdmin);

                return TransferResponse.builder()
                        .id(from.getId())
                        .fromUserId(transferRequest.getFromUserId())
                        .toUserId(transferRequest.getToUserId())
                        .nominalTransfer(transferRequest.getNominalTransfer())
                        .tax(1500L)
                        .total(transferRequest.getNominalTransfer()+1500)
                        .transDate(from.getTransDate())
                        .build();
            }
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "your balance is not enough");
        } else if (fromUserWallet == null ||  toUserWallet == null) {
            throw new  ResponseStatusException(HttpStatus.NOT_FOUND, "fromUserId or toUserId is not found");
        }else if (fromUserWallet != null &&  toUserWallet != null && fromUserWallet.getBalance()<50000) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "your balance is not enough");
        } else if (fromUserWallet != null &&  toUserWallet != null && fromUserWallet.getBalance()>50000 && transferRequest.getNominalTransfer()>25000000) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "maximal transfer is Rp.25.000.000");
        } else if (fromUserWallet != null &&  toUserWallet != null && fromUserWallet.getBalance()>50000 && transferRequest.getNominalTransfer()<25000000&&totalTransfer24hours>1000) {
            throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "maximal transfer for day is 3 times");
        } throw new  ResponseStatusException(HttpStatus.BAD_REQUEST, "check your transfer again");
    }

    @Override
    @Transactional
    public List<Transfer> getAllTransferHistoryByUserId(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user!=null) {
            return transferRepository.findAllTransfersByUserIdWithTransfer(userId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "your userId is not foud");
    }

    @Override
    public List<Transfer> getAllOutTransferHistoryByUserId(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user!=null) {
            return transferRepository.findAllByFromUserIdAndTransferOutIsNotNull(userId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "your userId is not foud");
    }

    @Override
    public List<Transfer> getAllInTransferHistoryByUserId(String userId) {
        User user = userRepository.findById(userId).orElse(null);
        if (user!=null) {
            return transferRepository.findAllByToUserIdAndTransferInIsNotNull(userId);
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "your userId is not foud");
    }
}
