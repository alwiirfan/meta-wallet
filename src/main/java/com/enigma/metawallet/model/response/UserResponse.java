package com.enigma.metawallet.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String dateOfBirth;
}
