package com.enigma.metawallet.repository;

import com.enigma.metawallet.entity.User;
import com.enigma.metawallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Query("select u.wallet from User u where u.id = :userId")
    Wallet findWalletByUserId(@Param("userId") String userId);

}
