package com.enigma.metawallet.controller;

import com.enigma.metawallet.model.request.TransferRequest;
import com.enigma.metawallet.model.response.CommonResponse;
import com.enigma.metawallet.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/transfer")
public class TransferController {
    private final TransferService transferService;

    @PostMapping
    private ResponseEntity<?> createNewTransferWithUserId(@RequestBody TransferRequest transferRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("your transfer is success")
                        .data(transferService.transferWithUserId(transferRequest))
                        .build());
    }

    @GetMapping(path = "/history/{userId}")
    public ResponseEntity<?> getAllTransferHistoryByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("get transfer history is success")
                        .data(transferService.getAllTransferHistoryByUserId(userId))
                        .build());
    }

    @GetMapping(path = "/outhistory/{userId}")
    public ResponseEntity<?> getAllOutTransferHistoryByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("get out transfer history is success")
                        .data(transferService.getAllOutTransferHistoryByUserId(userId))
                        .build());
    }

    @GetMapping(path = "/inhistory/{userId}")
    public ResponseEntity<?> getAllInTransferHistoryByUserId(@PathVariable String userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("get in transfer history is success")
                        .data(transferService.getAllInTransferHistoryByUserId(userId))
                        .build());
    }
}
