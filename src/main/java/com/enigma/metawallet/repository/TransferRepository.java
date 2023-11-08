package com.enigma.metawallet.repository;

import com.enigma.metawallet.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, String> {
}
