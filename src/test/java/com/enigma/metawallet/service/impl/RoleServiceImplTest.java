package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Role;
import com.enigma.metawallet.entity.roleContract.ERole;
import com.enigma.metawallet.repository.RoleRepository;
import com.enigma.metawallet.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class RoleServiceImplTest {

    private final RoleRepository roleRepository = mock(RoleRepository.class);
    private final RoleService roleService = new RoleServiceImpl(roleRepository);

    @Test
    void itShouldGetExistingRoleSuccessful() {
        ERole role = ERole.ROLE_USER;

        //dummy role
        Role dummyRole = Role.builder()
                .id("1")
                .role(role)
                .build();

        when(roleRepository.findByRole(role)).thenReturn(Optional.of(dummyRole));

        Role result = roleService.getOrSave(role);

        verify(roleRepository, times(1)).findByRole(role);
        verify(roleRepository, never()).save(any(Role.class));

        assertEquals(dummyRole, result);
    }

    @Test
    void itShouldGetRoleAndSaveNewRoleSuccessful() {
        ERole role = ERole.ROLE_USER;

        //dummy role
        Role dummyRole = Role.builder()
                .id("1")
                .role(role)
                .build();

        when(roleRepository.findByRole(role)).thenReturn(Optional.of(dummyRole));

        Role result = roleService.getOrSave(role);

        verify(roleRepository, times(1)).findByRole(role);
        verify(roleRepository, never()).save(any());

        assertEquals(dummyRole, result);
    }

    @Test
    public void testGetOrSaveRoleNotExists() {
        ERole mockRole = ERole.ROLE_USER;

        when(roleRepository.findByRole(mockRole)).thenReturn(Optional.empty());

        when(roleRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Role result = roleService.getOrSave(mockRole);

        assertEquals(mockRole, result.getRole());

        verify(roleRepository, times(1)).findByRole(mockRole);
        verify(roleRepository, times(1)).save(any());

    }

}
