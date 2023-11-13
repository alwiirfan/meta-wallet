package com.enigma.metawallet.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRegisterRequest {
    private String name;

    @NotBlank
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d#$@!%&*?]{8,20}$",
            message = "please must enter min 1 uppercase, min 1 lowercase, min 1 number, min 8 characters and max 20 characters")
    private String password;

    @NotBlank
    private String username;

    @NotBlank
    private String address;

    @NotBlank
    private String city;

    @NotBlank
    private String country;

    @NotBlank
    private String mobilePhone;

    @NotBlank
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4}$", message = "please use format dd-MM-yyyy")
    private String dateOfBirth;

}
