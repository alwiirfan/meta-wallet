package com.enigma.metawallet.model.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UserRegisterRequest {
    private String name;

    @NotBlank
    private String email;

    @Pattern(regexp = "^[a-zA-Z0-9]{10}")
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
