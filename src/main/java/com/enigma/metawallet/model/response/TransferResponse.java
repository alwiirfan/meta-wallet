package com.enigma.metawallet.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class TransferResponse {
    private String id;
    private String fromUserId;
    private String toUserId;
    private Long nominalTransfer;
    private Long tax;
    private Long total;
    private LocalDateTime transDate;
}