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
    private String deliverToUsername;
    private Long totalBalanceSent;
    private LocalDateTime transDate;
}