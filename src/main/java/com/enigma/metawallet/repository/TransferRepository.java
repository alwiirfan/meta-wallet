package com.enigma.metawallet.repository;

import com.enigma.metawallet.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, String> {

    @Query("SELECT t FROM Transfer t WHERE (t.fromUserId = :userId AND t.transferOut IS NOT NULL) OR (t.toUserId = :userId AND t.transferIn IS NOT NULL)")
    List<Transfer> findAllTransfersByUserIdWithTransfer(@Param("userId") String userId);
    List<Transfer> findAllByToUserIdAndTransferInIsNotNull(String fromUserId);
    List<Transfer> findAllByFromUserIdAndTransferOutIsNotNull(String fromUserId);

    @Query("SELECT COUNT(t) FROM Transfer t " +
            "WHERE t.fromUserId = :fromUserId " +
            "AND t.transferOut IS NOT NULL " +
            "AND t.transDate > :twentyFourHoursAgo")
    Long countByFromUserIdAndTransferOutIsNotNullAndTransDateAfter(
            @Param("fromUserId") String fromUserId,
            @Param("twentyFourHoursAgo") LocalDateTime twentyFourHoursAgo);
}
