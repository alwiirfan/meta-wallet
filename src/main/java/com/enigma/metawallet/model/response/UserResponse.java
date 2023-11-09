package com.enigma.metawallet.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserResponse {
    private String id;
    private String name;
    private String email;
    private String username;
    private String address;
    private String mobilePhone;
    private String country;
    private String city;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    private WalletResponse wallet;
}
