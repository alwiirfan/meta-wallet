package com.enigma.metawallet.repository;

import com.enigma.metawallet.entity.Admin;
import com.enigma.metawallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    @Query("select u.wallet from Admin u where u.id = :adminId")
    Wallet findWalletByAdminId(@Param("adminId") Long adminId);

    Admin findByEmail(String email);
}
