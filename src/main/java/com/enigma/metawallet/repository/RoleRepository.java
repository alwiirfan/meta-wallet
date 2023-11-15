package com.enigma.metawallet.repository;

import com.enigma.metawallet.entity.Role;
import com.enigma.metawallet.entity.roleContract.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);


}
