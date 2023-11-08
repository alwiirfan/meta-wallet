package com.enigma.metawallet.service.impl;

import com.enigma.metawallet.entity.Role;
import com.enigma.metawallet.entity.roleContract.ERole;
import com.enigma.metawallet.repository.RoleRepository;
import com.enigma.metawallet.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getOrSave(ERole role) {
        return roleRepository.findByRole(role).orElseGet(() ->  roleRepository.save(Role.builder()
                .role(role)
                .build()));
    }
}
