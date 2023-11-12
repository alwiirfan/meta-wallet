package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.model.response.AdminResponse;
import com.enigma.metawallet.repository.AdminRepository;
import com.enigma.metawallet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    @Override
    public Admin create(Admin admin) {
        return adminRepository.save(admin);
    }

    @Override
    public Admin getById(Long id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin is not found"));
    }

    @Override
    public Page<AdminResponse> getAllAdmin(Integer size, Integer page) {
        Pageable pageable = PageRequest.of(page, size);

        Page<Admin> adminList = adminRepository.findAll(pageable);

        List<AdminResponse> responses = adminList.stream().map(this::toAdminResponse).collect(Collectors.toList());

        return new PageImpl<>(responses, pageable, adminList.getTotalElements());
    }

    private AdminResponse toAdminResponse(Admin admin){
        return AdminResponse.builder()
                .id(admin.getId())
                .name(admin.getName())
                .email(admin.getEmail())
                .username(admin.getUserCredential().getUsername())
                .build();
    }
}
