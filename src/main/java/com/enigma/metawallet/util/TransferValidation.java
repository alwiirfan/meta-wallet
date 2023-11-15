package com.enigma.metawallet.util;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.entity.roleContract.ERole;
import com.enigma.metawallet.model.request.TransferRequest;
import com.enigma.metawallet.repository.AdminRepository;
import com.enigma.metawallet.repository.UserRepository;
import com.enigma.metawallet.service.impl.UserDetailImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
@RequiredArgsConstructor
public class TransferValidation {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public Boolean checkAccess(String userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetailImpl userDetail = (UserDetailImpl) authentication.getPrincipal();
        User user = userRepository.findById(userId).orElse(null);
        Admin admin = adminRepository.findByEmail(userDetail.getEmail());
        if ((user != null && user.getEmail().equals(userDetail.getEmail())) || (admin!=null && admin.getId().equals(1L))) {
            return true;
        }return false;
    }


    public void validateTransfer(TransferRequest transferRequest, Wallet fromUserWallet, Wallet toUserWallet, Long totalTransfer24hours) {
        // Pemeriksaan awal
        if (transferRequest.getNominalTransfer() <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nominal transfer must be greater than 0");
        }

        // Validasi ketersediaan wallet
        if (fromUserWallet == null || toUserWallet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "fromUserId or toUserId is not found");
        }


        // Validasi maksimal transfer
        if (transferRequest.getNominalTransfer() > 25000000) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximal transfer is Rp.25,000,000");
        }

        // Validasi maksimal transfer harian
        if (transferRequest.getNominalTransfer() < 25000000 && totalTransfer24hours >= 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Maximal transfer for the day is 5 times");
        }

        // Validasi sisa saldo setelah transaksi harus lebih dari Rp.15,000
        if (fromUserWallet.getBalance()-transferRequest.getNominalTransfer()<15000){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your balance is not enough");
        }
    }

}
