package com.enigma.metawallet.model.request;

import com.enigma.metawallet.entity.UserCredential;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRequest {
    private String name;

    @NotBlank
    private String address;

    @NotBlank
    private String mobilePhone;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String dateOfBirth;

    @NotBlank
    private String email;

    @NotBlank
    private UserCredential userCredential;
}
