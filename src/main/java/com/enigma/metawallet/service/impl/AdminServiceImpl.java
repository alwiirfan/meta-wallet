package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.repository.AdminRepository;
import com.enigma.metawallet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }
}
