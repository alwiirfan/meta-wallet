package com.enigma.metawallet.controller;

import com.enigma.metawallet.model.response.AdminResponse;
import com.enigma.metawallet.model.response.CommonResponse;
import com.enigma.metawallet.model.response.PagingResponse;
import com.enigma.metawallet.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @GetMapping
    public ResponseEntity<?> getAllAdmin(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                         @RequestParam(value = "size", required = false, defaultValue = "10") Integer size){

        Page<AdminResponse> adminResponses = adminService.getAllAdmin(size, page);
        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<List<AdminResponse>>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Successful to get all admin")
                        .data(adminResponses.getContent())
                        .paging(PagingResponse.builder()
                                .count(adminResponses.getNumber())
                                .page(adminResponses.getNumberOfElements())
                                .size(adminResponses.getSize())
                                .totalPages(adminResponses.getTotalPages())
                                .build())
                        .build());
    }

}
