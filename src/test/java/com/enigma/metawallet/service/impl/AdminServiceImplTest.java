package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.entity.UserCredential;
import com.enigma.metawallet.entity.Wallet;
import com.enigma.metawallet.model.response.AdminResponse;
import com.enigma.metawallet.repository.AdminRepository;
import com.enigma.metawallet.service.AdminService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@SpringBootTest
class AdminServiceImplTest {
    private final AdminRepository adminRepository = mock(AdminRepository.class);
    private final AdminService adminService = new AdminServiceImpl(adminRepository);

    @Test
    void createAdmin() {

        Admin admin = new Admin();
        admin.setId(4387837L);
        admin.setName("contoh");

        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        Admin createAdmin = adminService.create(admin);

        verify(adminRepository, times(1)).save(admin);

        assertEquals(4387837L, createAdmin.getId());
        assertEquals("contoh", createAdmin.getName());
    }

    @Test
    void getById() {
        Long adminId = 23456L;
        Admin admin = new Admin();
        admin.setId(23456L);
        admin.setName("contoh");

        when(adminRepository.findById(admin.getId())).thenReturn(Optional.of(admin));

        Admin retrievedAdmin = adminService.getById(admin.getId());

        verify(adminRepository, times(1)).findById(retrievedAdmin.getId());

        assertEquals(adminId, retrievedAdmin.getId());
        assertEquals("contoh", retrievedAdmin.getName());

    }

    @Test
    void getAllAdmin() {
        int page = 0;
        int size = 10;
        PageRequest pageable = PageRequest.of(page, size);

        List<Admin> admin = new ArrayList<>();
        admin.add(new Admin(23456L, "admin b", "b@gmail.com", new UserCredential(), new Wallet()));
        admin.add(new Admin(12345L, "admin a", "a@gmail.com", new UserCredential(), new Wallet()));

        Page<Admin> mockAdminPage = new PageImpl<>(admin, pageable, admin.size());

        when(adminRepository.findAll(pageable)).thenReturn(mockAdminPage);

        Page<AdminResponse> result = adminService.getAllAdmin(size, page);

        verify(adminRepository, times(1)).findAll(pageable);

        assertEquals(mockAdminPage.getTotalElements(), result.getTotalElements());
        assertEquals(admin.size(), result.getContent().size());



//        for (i = 0; <admin.size(); i++){
//            assertEquals(admin.get(i).getId(), result.getContent().get(i).getId());


        }
    }
