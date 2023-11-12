package com.enigma.metawallet.service;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.model.response.AdminResponse;
import org.springframework.data.domain.Page;


public interface AdminService {

    Admin create(Admin admin);
    Admin getById(Long id);
    Page<AdminResponse> getAllAdmin(Integer size, Integer page);
}
